package party.threebody.herd.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.herd.dao.*;
import party.threebody.herd.domain.*;
import party.threebody.herd.util.ImageConverter;
import party.threebody.herd.util.ImageMetaUtils;
import party.threebody.herd.util.MediaType;
import party.threebody.herd.util.RepoFileTypeUtils;
import party.threebody.skean.SkeanException;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.AffectCounter;
import party.threebody.skean.util.DateTimeFormatters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.*;

/**
 * MediaPath
 * Media
 *
 * @author hzk
 */
@Service
public class HerdService {

    private static final Logger logger = LoggerFactory.getLogger(HerdService.class);
    @Autowired
    RepoDao repoDao;
    @Autowired
    MediaDao mediaDao;
    @Autowired
    MediaPathDao mediaPathDao;
    @Autowired
    RepoLogItemDao repoLogItemDao;
    @Autowired
    ImageMediaDao imageMediaDao;
    @Autowired
    Environment env;

    //------- queries --------
    public List<Repo> listAliveRepos() {
        return repoDao.listByState("A");
    }

    public List<Repo> listRepos(QueryParamsSuite qps) {
        return repoDao.readList(qps);
    }

    public int countRepos(QueryParamsSuite qps) {
        return repoDao.readCount(qps);
    }


    List<MediaPath> listMediaPaths() {
        return mediaPathDao.readList(null);
    }

    public List<MediaPath> listMediaPathByRepoNames(List<String> repoNames) {
        return mediaPathDao.listByRepoNames(repoNames);
    }


    List<MediaPath> listMediaPathsBySyncTime(LocalDateTime syncTime) {
        return mediaPathDao.listBySyncTime(syncTime);
    }

    public List<Media> listMediasByRepoNames(List<String> repoNames) {
        List<MediaPath> mediaPaths = listMediaPathByRepoNames(repoNames);
        Set<String> hashs = mediaPaths.stream().map(MediaPath::getHash).collect(toSet());
        return mediaDao.listByHashs(hashs);
    }

    List<Media> listMediasBySyncTime(LocalDateTime syncTime) {
        return mediaDao.listBySyncTime(syncTime);
    }

    public List<Media> listMedias(QueryParamsSuite qps) {
        return mediaDao.readList(qps);
    }


    public int countMedias(QueryParamsSuite qps) {
        return mediaDao.readCount(qps);
    }


    //-----------sync logic ----------------
    public AffectCount clearAll() {
        int rnd = 0;
        rnd += mediaPathDao.deleteSome(null);
        rnd += mediaDao.deleteSome(null);
        rnd += imageMediaDao.deleteSome(null);
        return AffectCount.ofOnlyDeleted(rnd);
    }


    public AffectCount clearMediaPaths(List<Repo> repos) {
        LocalDateTime actionTime=LocalDateTime.now();
        logger.info("clearing MediaPaths @ {} ...",actionTime);
        AffectCount afc = AffectCount.ofOnlyDeleted(mediaPathDao.deleteByRepoNames(repos.stream().map(Repo::getName).collect(toList())));
        logger.info(afc.toString("MediaPath"));
        createRepoLogByRepos(actionTime,"clearMediaPaths",repos,afc);
        return afc;
    }

    public AffectCount clearMedias(List<Repo> repos) {
        LocalDateTime actionTime=LocalDateTime.now();
        logger.info("clearing Medias @ {} ...",actionTime);
        List<Media> medias = listMediasByRepoNames(repos.stream().map(Repo::getName).collect(toList()));
        AffectCount afc = AffectCount.ofOnlyDeleted(medias.stream().mapToInt(mediaDao::deleteByExample).sum());
        logger.info(afc.toString("Media"));
        createRepoLogByRepos(actionTime,"clearMedias",repos,afc);
        return afc;
    }

    public AffectCount clearImageMedias(List<Repo> repos) {
        LocalDateTime actionTime=LocalDateTime.now();
        logger.info("clearing MediaPaths @ {} ...",actionTime);
        List<Media> medias = listMediasByRepoNames(repos.stream().map(Repo::getName).collect(toList()));
        int rnd = medias.stream().map(Media::getHash).mapToInt(imageMediaDao::delete).sum();
        AffectCount afc = AffectCount.ofOnlyDeleted(rnd);
        logger.info(afc.toString("ImageMedia"));
        createRepoLogByRepos(actionTime,"clearImageMedias",repos,afc);
        return afc;
    }

    /**
     * a through-all action, contains:
     * # synchonizeMediaPaths
     * # synchonizeMedias
     * # analyzeMedias
     *
     * @param repos
     * @return
     */
    public AffectCount synchonizeAndAnalyzeAll(List<Repo> repos) {
        final LocalDateTime syncTime = LocalDateTime.now();
        AffectCount count = new AffectCount();
        count = count.add(synchonizeMediaPaths(repos, syncTime));
        List<MediaPath> newMediaPaths = listMediaPathsBySyncTime(syncTime);
        count = count.add(synchonizeMedias(newMediaPaths, syncTime));
        List<Media> newMedias = listMediasBySyncTime(syncTime);
        count = count.add(analyzeMedias(newMedias, syncTime));
        return count;
    }


    /**
     * scan and hash all files in all repos, and record hash and path to MediaPath data
     * >>>data flow: Repo -> MediaPath
     *
     * @return MediaPath's AffectCount
     */
    public AffectCount synchonizeMediaPaths(List<Repo> repos, LocalDateTime syncTime) {
        logger.info("synchonizing MediaPaths @ {} ...", DateTimeFormatters.DEFAULT.format(syncTime));
        final long t0 = currentTimeMillis();
        final AtomicInteger totalFiles = new AtomicInteger();
        final AtomicLong totalSizeInBytes = new AtomicLong();
        final List<MediaPath> mediaPathsToHashes = new ArrayList<>();
        final Set<String> oldPathStrs = listMediaPaths().stream().map(mp -> mp.getPath()).collect(Collectors.toSet());
        final Predicate<Path> fileFilter = p -> Files.isRegularFile(p) && !oldPathStrs.contains(p.toString());
        ;
        repos.forEach(repo -> {
            logger.info("scanning repo[{}] ...", repo.getName());
            Path pRoot = Paths.get(repo.getAbsPath());
            try {
                final int numFiles = (int) Files.walk(pRoot).filter(fileFilter).count();
                totalFiles.addAndGet(numFiles);
                AtomicInteger i = new AtomicInteger();
                Files.walk(pRoot).filter(fileFilter).forEach(path -> {
                    long t1 = currentTimeMillis();
                    try {
                        String hash = DigestUtils.sha1Hex(Files.newInputStream(path));
                        totalSizeInBytes.addAndGet(Files.size(path));
                        mediaPathsToHashes.add(new MediaPath(hash, path.toString(), null, repo.getName(), syncTime));
                        logger.info("[{}/{}] hashed, {}ms used : {} << {}",
                                i.incrementAndGet(), numFiles, System.currentTimeMillis() - t1, hash, path);
                    } catch (IOException e) {
                        createRepoLogWhenFail(syncTime,"syncMediaPaths.hash","absPath",path.toString(),e);
                        logger.warn("[{}/{}] hash failed! {}ms used : {} ",
                                i.incrementAndGet(), numFiles, System.currentTimeMillis() - t1, e.getMessage());
                    }
                });
            } catch (IOException e) {
                throw new SkeanException("fail to scan repo: " + repo.getName(), e);
            }
        });
        logger.info("scanning finished: {} files found; {} bytes in all files.",
                totalFiles.get(), totalSizeInBytes.get());

        int rns = mediaPathsToHashes.size();
        int rnc = mediaPathsToHashes.stream().map(mp -> {
            int rnc1 = 0;
            try {
                rnc1 = mediaPathDao.create(mp);
            } catch (Exception e) {
                createRepoLogWhenFail(syncTime,"syncMediaPaths.createMP","absPath",mp.getPath(),e);
                logger.warn("create MediaPath failed : " + mp.getPath(), e);
            }
            return rnc1;
        }).reduce((s, a) -> s + a).orElse(0);
        AffectCount afc = new AffectCount(rnc, 0, 0, rns - rnc, 0).tillNow(t0);
        logger.info(afc.toString("MediaPath"));
        createRepoLogByRepos(syncTime,"syncMediaPaths",repos,afc);
        return afc;
    }


    /**
     * according to MediaPaths, rebuild Media data
     * >>>data flow: MediaPath -> Media
     *
     * @return Media's AffectCount
     */
    @Transactional
    public AffectCount synchonizeMedias(List<MediaPath> mediaPaths, LocalDateTime syncTime) {
        logger.info("synchonizing Medias @ {} ...", DateTimeFormatters.DEFAULT.format(syncTime));
        Map<String, Set<String>> hash2pathsMap = mediaPaths.stream()
                .collect(
                        groupingBy(
                                MediaPath::getHash,
                                mapping(MediaPath::getPath, toSet())
                        )
                );
        final int mediaPathCnt = mediaPaths.size();
        final int hashCnt = hash2pathsMap.keySet().size();
        final AffectCounter counter = new AffectCounter();
        hash2pathsMap.forEach((hash, paths) -> {
            String path0 = paths.iterator().next();
            AffectCount afc = synchonizeMedia(hash, path0, syncTime);
            counter.addAndGet(afc);
            logger.debug("[{}/{}] : {}", counter.result().total(), hashCnt, path0);
        });
        logger.debug("{} mediaPaths found. {} redundant files found.", mediaPathCnt, mediaPathCnt - hashCnt);
        logger.info("synchonize Medias finished. {}", counter.result().toString("Media"));
        createRepoLog(syncTime,"syncMediaPaths","","","OK",counter.result().toString());
        return counter.result();
    }


    /**
     * save Media if not exists
     */
    private AffectCount synchonizeMedia(String hash, String pathStr, LocalDateTime syncTime) {
        long t1 = currentTimeMillis();
        int rnc = 0;
        try {
            Media old = mediaDao.readOne(hash);
            if (old == null) {
                Path path = Paths.get(pathStr);
                Media media = new Media();
                media.setHash(hash);
                media.setTypeAndSubtype(RepoFileTypeUtils.guessRepoFileTypeByPath(path));
                media.setSize((int) Files.size(path));
                media.setSyncTime(syncTime);
                rnc = mediaDao.create(media);
            }
            return AffectCount.ofOnlyCreated(rnc).tillNow(t1);
        } catch (Exception e) {
            createRepoLogWhenFail(syncTime,"synchonizeMedia","absPath", pathStr,e);
            return AffectCount.ONE_FAILED.tillNow(t1);
        }
    }

    /**
     * >>>data flow: MediaPath -> ImageMedia or else alike
     */
    public AffectCount analyzeMedias(List<Media> medias, LocalDateTime analyzeTime) {
        logger.info("analyzing Medias @ {} ...", DateTimeFormatters.DEFAULT.format(analyzeTime));
        List<String> mediaHashs = medias.stream().map(Media::getHash).collect(toList());
        List<ImageMedia> mediasAnalyzed = imageMediaDao.listByHashs(mediaHashs);
        List<Media> mediasUnanalzed = medias.stream().filter(m -> !mediasAnalyzed.contains(m)).collect(toList());

        final AtomicInteger i = new AtomicInteger();
        final int mediaCnt = mediasUnanalzed.size();
        AffectCount count = mediasUnanalzed.stream()
                .map(m -> {
                    AffectCount afc = analyzeMedia(m, analyzeTime);
                    logger.debug("[{}/{}] analyzed : {}", i.incrementAndGet(), mediaCnt, m.getPath0Path());
                    return afc;
                })
                .reduce(AffectCount::add)
                .orElse(AffectCount.NOTHING);
        logger.info("analyzing finished. {}", count.toString("ImageMedia"));
        createRepoLog(analyzeTime,"analyzeMedias","","","OK",count.toString());
        return count;
    }

    /**
     * analyze the file and save its metadata if not exists
     */
    public AffectCount analyzeMedia(Media media, LocalDateTime analyzeTime) {
        final long t1 = currentTimeMillis();
        final String hash = media.getHash();
        final String absPath = media.getPath0Path();   //regard as absPath
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(absPath));
            ImageMedia imageMedia = ImageMetaUtils.parseExifInfo(inputStream);
            imageMedia.setHash(hash);
            imageMediaDao.create(imageMedia);
            return AffectCount.ONE_CREATED.tillNow(t1);
        } catch (Exception e) {
            createRepoLogWhenFail(analyzeTime,"analyzeMedia","hash", hash,e);
            return AffectCount.ONE_FAILED.tillNow(t1);
        }

    }




    public AffectCount convertToJpgByMedias(List<Media> medias, LocalDateTime convertTime, ImageConverter converter) {
        final int mediaCnt = medias.size();
        final AtomicInteger i = new AtomicInteger();
        final String localThumbnailRepoPath = getHerdLocalThumbnailRepoPath();
        final Path destDirPath = Paths.get(localThumbnailRepoPath, converter.getName());
        makeSureDirectoryExists(destDirPath);
        logger.info("thumbing Medias ...");
        AffectCount count = medias.stream().map(m -> {
            File srcFile = Paths.get(m.getPath0Path()).toFile();
            String fileName = m.getHash() + "." + MediaType.JPEG.getSuffix();
            File destFile = destDirPath.resolve(fileName).toFile();
            logger.debug("[{}/{}] converted to JPG : {}", i.incrementAndGet(), mediaCnt, m.getPath0Path());
            return convertToJPG(srcFile, destFile, convertTime, converter);
        })
                .reduce(AffectCount::add)
                .orElse(AffectCount.NOTHING);
        logger.info("convert to JPG finished. {}", count.toString("JPG File"));
        createRepoLog(convertTime,"convertToJpgs","converter",converter.getName(),"OK",count.toString());
        return count;

    }

    public AffectCount convertToJPG(File srcImage, File destImage, LocalDateTime convertTime, ImageConverter converter) {
        final long t1 = currentTimeMillis();
        try {
            converter.convertAndWriteToJpgFile(srcImage, destImage);
            return AffectCount.ONE_CREATED.tillNow(t1);
        } catch (Exception e) {
            createRepoLogWhenFail(convertTime,"convertToJPG","path", srcImage.getPath(),e);
            return AffectCount.ONE_FAILED.tillNow(t1);
        }
    }

    public String getHerdLocalThumbnailRepoPath() {
        return env.getProperty("herd.localThumbnailRepoPath");
    }

    static void makeSureDirectoryExists(Path dirPath) {
        if (!Files.isDirectory(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                throw new SkeanException("cannot create directory", e);
            }
        }
    }

    //---------- db logging----------
    /**
     * @param actionTime action time
     * @param actionType action type
     * @param entityKey  entity's key
     * @param entityVal  entity's val
     * @param result     result message
     * @param desc       desciption
     * @return RNA
     */
    private int createRepoLog(LocalDateTime actionTime, String actionType, String entityKey, String entityVal, String result, String desc) {
        RepoLogItem repoLogItem = new RepoLogItem(actionTime, actionType, entityKey, entityVal, result, desc);
        return repoLogItemDao.create(repoLogItem);
    }
    private int createRepoLogWhenFail(LocalDateTime actionTime, String actionType, String entityKey, String entityVal, Exception e){
        return createRepoLog(actionTime,actionType,entityKey,entityVal,"FAIL",e.getMessage());
    }
    private int createRepoLogByRepos(LocalDateTime actionTime, String actionType, List<Repo> repos, AffectCount afc){
        String repoNames=repos.stream().map(Repo::getName).reduce((s,a)->s+", "+a).orElse("");
        return createRepoLog(actionTime,actionType,"repoNames",repoNames,"OK",afc.toString());
    }

    public byte[] getMediaFileContent(String hash) throws IOException {
        Media one = mediaDao.readOne(hash);
        return Files.readAllBytes(Paths.get(one.getPath0Path()));
    }

}

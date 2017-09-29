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
import party.threebody.skean.core.SkeanException;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.core.result.Count;
import party.threebody.skean.core.result.Counts;
import party.threebody.skean.jdbc.rs.DualColsBean;
import party.threebody.skean.jdbc.rs.TripleColsBean;
import party.threebody.skean.lang.DateTimeFormatters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<ImageMedia> listImageMedias(QueryParamsSuite qps) {
        return imageMediaDao.list(qps);
    }

    public int countImageMedias(QueryParamsSuite qps) {
        return imageMediaDao.readCount(qps);
    }

    public List<DualColsBean<LocalDate, Integer>> countImageMediasByDate() {
        return imageMediaDao.countByDate();
    }

    public List<TripleColsBean<Integer, Integer, Integer>> countImageMediasByMonth() {
        return imageMediaDao.countByMonth();
    }

    public List<DualColsBean<Integer, Integer>> countImageMediasByYear() {
        return imageMediaDao.countByYear();
    }

    //----- thin CRUDs ---------
    public int createRepo(Repo repo) {
        repo.setSaveTime(LocalDateTime.now());
        return repoDao.create(repo);
    }

    public int deleteRepo(String repoName) {
        return repoDao.delete(repoName);
    }

    public int updateRepo(Repo repo, String repoName) {
        repo.setSaveTime(LocalDateTime.now());
        return repoDao.update(repo, repoName);
    }


    //-----------sync logic ----------------
    public Count clearAll() {
        int rnd = 0;
        rnd += mediaPathDao.deleteSome(null);
        rnd += mediaDao.deleteSome(null);
        rnd += imageMediaDao.deleteSome(null);
        return Counts.deleted(rnd);
    }


    public Count clearMediaPaths(List<Repo> repos) {
        LocalDateTime actionTime = LocalDateTime.now();
        logger.info("clearing MediaPaths @ {} ...", actionTime);
        Count afc = Counts.of(MediaPath.class).deleted(mediaPathDao.deleteByRepoNames(repos.stream().map(Repo::getName).collect(toList())));
        logger.info(afc.toString());
        createRepoLogByRepos(actionTime, "clearMediaPaths", repos, afc);
        return afc;
    }

    public Count clearMedias(List<Repo> repos) {
        LocalDateTime actionTime = LocalDateTime.now();
        logger.info("clearMedias @ {} ...", actionTime);
        List<Media> medias = listMediasByRepoNames(repos.stream().map(Repo::getName).collect(toList()));
        Count afc = Counts.of(Media.class).deleted(medias.stream().mapToInt(mediaDao::deleteByExample).sum());
        logger.info(afc.toString());
        createRepoLogByRepos(actionTime, "clearMedias", repos, afc);
        return afc;
    }

    public Count clearImageMedias(List<Repo> repos) {
        LocalDateTime actionTime = LocalDateTime.now();
        logger.info("clearImageMedias @ {} ...", actionTime);
        List<Media> medias = listMediasByRepoNames(repos.stream().map(Repo::getName).collect(toList()));
        int rnd = medias.stream().map(Media::getHash).mapToInt(imageMediaDao::delete).sum();
        Count afc = Counts.of(ImageMedia.class).deleted(rnd);
        logger.info(afc.toString());
        createRepoLogByRepos(actionTime, "clearImageMedias", repos, afc);
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
    public List<Count> synchonizeAndAnalyzeAll(List<Repo> repos) {
        final LocalDateTime syncTime = LocalDateTime.now();
        List<Count> cnt1 = synchonizeMediaPaths(repos, syncTime);
        List<MediaPath> newMediaPaths = listMediaPathsBySyncTime(syncTime);
        Count cnt2 = synchonizeMedias(newMediaPaths, syncTime);
        List<Media> newMedias = listMediasBySyncTime(syncTime);
        Count cnt3 = analyzeMedias(newMedias, syncTime);
        return Stream.of(cnt1, Arrays.asList(cnt2, cnt3)).flatMap(List::stream).collect(toList());
    }


    /**
     * scan and hash all files in all repos, and record hash and path to MediaPath data
     * >>>data flow: Repo -> MediaPath
     *
     * @return MediaPath's Count
     */
    public List<Count> synchonizeMediaPaths(List<Repo> repos, LocalDateTime syncTime) {
        logger.info("synchonizing MediaPaths @ {} ...", DateTimeFormatters.DEFAULT.format(syncTime));
        final long t0 = currentTimeMillis();
        final AtomicInteger totalFiles = new AtomicInteger();
        final AtomicInteger totalFilesToHash = new AtomicInteger();
        final AtomicInteger totalFilesToHashFailed = new AtomicInteger();
        final AtomicLong totalSizeInBytes = new AtomicLong();
        final List<MediaPath> mediaPathsToHashes = new ArrayList<>();
        final Set<String> oldPathStrs = listMediaPaths().stream().map(mp -> mp.getPath()).collect(Collectors.toSet());
        final Predicate<Path> fileFilter = p -> Files.isRegularFile(p) && !oldPathStrs.contains(p.toString());
        ;
        repos.forEach(repo -> {
            logger.info("scanning repo[{}] ...", repo.getName());
            Path pRoot = Paths.get(repo.getAbsPath());
            try {
                final int n = (int) Files.walk(pRoot).filter(fileFilter).count();
                totalFiles.addAndGet(n);
                AtomicInteger i = new AtomicInteger();
                AtomicInteger j = new AtomicInteger();
                Files.walk(pRoot).filter(fileFilter).forEach(path -> {
                    long t1 = currentTimeMillis();
                    try {
                        String hash = DigestUtils.sha1Hex(Files.newInputStream(path));
                        totalSizeInBytes.addAndGet(Files.size(path));
                        mediaPathsToHashes.add(new MediaPath(hash, path.toString(), null, repo.getName(), syncTime));
                        logger.info("[{}/{}] hashed, {}ms used : {} << {}",
                                i.incrementAndGet(), n, System.currentTimeMillis() - t1, hash, path);
                    } catch (IOException e) {
                        j.incrementAndGet();
                        createRepoLogWhenFail(syncTime, "syncMediaPaths.hash", "absPath", path.toString(), e);
                        logger.warn("[{}/{}] hash failed! {}ms used : {} ",
                                i.incrementAndGet(), n, System.currentTimeMillis() - t1, e.getMessage());
                    }
                });
                totalFiles.addAndGet(n);
                totalFilesToHash.addAndGet(i.get());
                totalFilesToHashFailed.addAndGet(j.get());
            } catch (IOException e) {
                throw new SkeanException("fail to scan repo: " + repo.getName(), e);
            }
        });
        logger.info("scanning finished: {} files found; {} bytes in all files.",
                totalFiles.get(), totalSizeInBytes.get());
        Count hashCnt = Counts.of("FileToHash")
                .completed(totalFilesToHash.get())
                .failed(totalFilesToHashFailed.get())
                .skipped(totalFiles.get() - totalFilesToHash.get()).since(t0);
        final long t1 = currentTimeMillis();
        int totalMediaPaths = mediaPathsToHashes.size();
        int totalMediaPathsCreated = mediaPathsToHashes.stream().map(mp -> {
            try {
                return mediaPathDao.create(mp);
            } catch (Exception e) {
                createRepoLogWhenFail(syncTime, "syncMediaPaths.createMP", "absPath", mp.getPath(), e);
                logger.warn("create MediaPath failed : " + mp.getPath(), e);
                return 0;
            }
        }).reduce((s, a) -> s + a).orElse(0);

        Count mpCnt = Counts.of("MediaPath")
                .created(totalMediaPathsCreated)
                .failed(totalMediaPaths - totalMediaPathsCreated).since(t1);
        createRepoLogByRepos(syncTime, "syncMediaPaths", repos, mpCnt);
        logger.info(mpCnt.toString());
        logger.info(mpCnt.toString());
        return Counts.merge(hashCnt, mpCnt);
    }


    /**
     * according to MediaPaths, rebuild Media data
     * >>>data flow: MediaPath -> Media
     *
     * @return Media's Count
     */
    @Transactional
    public Count synchonizeMedias(List<MediaPath> mediaPaths, LocalDateTime syncTime) {
        logger.info("synchonizing Medias @ {} ...", DateTimeFormatters.DEFAULT.format(syncTime));
        Map<String, Set<String>> hash2pathsMap = mediaPaths.stream()
                .collect(
                        groupingBy(
                                MediaPath::getHash,
                                mapping(MediaPath::getPath, toSet())
                        )
                );
        final int totalMediaPath = mediaPaths.size();
        final int totalHashs = hash2pathsMap.keySet().size();
        AtomicInteger i = new AtomicInteger();
        Count cnt = hash2pathsMap.keySet().stream()
                .map(hash -> {
                    String path0 = hash2pathsMap.get(hash).iterator().next();
                    logger.debug("[{}/{}] : {}", i.incrementAndGet(), totalHashs, path0);
                    return synchonizeMedia(hash, path0, syncTime);
                })
                .reduce(Counts.of(Media.class), Count::add)
                .skipped(totalMediaPath - totalHashs);

        logger.debug("{} mediaPaths found. {} redundant files found.", totalMediaPath, totalMediaPath - totalHashs);
        logger.info("synchonize Medias finished. {}", cnt);
        createRepoLog(syncTime, "syncMediaPaths", "", "", "OK", cnt);
        return cnt;
    }


    /**
     * save Media if not exists
     */
    private Count synchonizeMedia(String hash, String pathStr, LocalDateTime syncTime) {
        long t1 = currentTimeMillis();
        int rnd = 0;
        try {
            Media old = mediaDao.readOne(hash);
            if (old == null) {
                Path path = Paths.get(pathStr);
                Media media = new Media();
                media.setHash(hash);
                media.setTypeAndSubtype(RepoFileTypeUtils.guessRepoFileTypeByPath(path));
                media.setSize((int) Files.size(path));
                media.setSyncTime(syncTime);
                rnd = mediaDao.create(media);
            }
            return Counts.deleted(rnd).since(t1);
        } catch (Exception e) {
            createRepoLogWhenFail(syncTime, "synchonizeMedia", "absPath", pathStr, e);
            return Counts.failedOne().since(t1);
        }
    }

    /**
     * >>>data flow: MediaPath -> ImageMedia or else alike
     */
    public Count analyzeMedias(List<Media> medias, LocalDateTime analyzeTime) {
        logger.info("analyzing Medias @ {} ...", DateTimeFormatters.DEFAULT.format(analyzeTime));
        List<String> mediaHashs = medias.stream().map(Media::getHash).collect(toList());
        List<ImageMedia> mediasAnalyzed = imageMediaDao.listByHashs(mediaHashs);
        List<Media> mediasUnanalyzed = medias.stream().filter(m -> !mediasAnalyzed.contains(m)).collect(toList());

        final AtomicInteger i = new AtomicInteger();
        final int totalMediasUnanalyzed = mediasUnanalyzed.size();
        Count cnt = mediasUnanalyzed.stream()
                .map(m -> {
                    Count cnt1 = analyzeMedia(m, analyzeTime);
                    logger.debug("[{}/{}] analyzed : {}", i.incrementAndGet(), totalMediasUnanalyzed, m.getPath0Path());
                    return cnt1;
                })
                .reduce(Counts.of("MediasToAnalyze"), Count::add)
                .skipped(mediasAnalyzed.size());
        logger.info("analyze Medias finished. {}", cnt);
        createRepoLog(analyzeTime, "analyzeMedias", "", "", "OK", cnt);
        return cnt;
    }

    /**
     * analyze the file and save its metadata if not exists
     */
    public Count analyzeMedia(Media media, LocalDateTime analyzeTime) {
        final long t1 = currentTimeMillis();
        final String hash = media.getHash();
        final String absPath = media.getPath0Path();   //regard as absPath
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(absPath));
            ImageMedia imageMedia = ImageMetaUtils.parseExifInfo(inputStream);
            imageMedia.setHash(hash);
            imageMediaDao.create(imageMedia);
            return Counts.createdOne().since(t1);
        } catch (Exception e) {
            createRepoLogWhenFail(analyzeTime, "analyzeMedia", "hash", hash, e);
            return Counts.failedOne().since(t1);
        }

    }


    public Count convertToJpgByMedias(List<Media> medias, LocalDateTime convertTime, ImageConverter converter) {
        final int mediaCnt = medias.size();
        final AtomicInteger i = new AtomicInteger();
        final String localThumbnailRepoPath = getHerdLocalThumbnailRepoPath();
        final Path destDirPath = Paths.get(localThumbnailRepoPath, converter.getName());
        makeSureDirectoryExists(destDirPath);
        logger.info("thumbing Medias ...");
        Count cnt = medias.stream()
                .map(m -> {
                    File srcFile = Paths.get(m.getPath0Path()).toFile();
                    String fileName = m.getHash() + "." + MediaType.JPEG.getSuffix();
                    File destFile = destDirPath.resolve(fileName).toFile();
                    logger.debug("[{}/{}] converted to JPG : {}", i.incrementAndGet(), mediaCnt, m.getPath0Path());
                    return convertToJPG(srcFile, destFile, convertTime, converter);
                })
                .reduce(Counts.of("ImagesToConvert"), Count::add);
        logger.info("convert to JPG finished. {}", cnt);
        createRepoLog(convertTime, "convertToJpgs", "converter", converter.getName(), "OK", cnt);
        return cnt;

    }

    public Count convertToJPG(File srcImage, File destImage, LocalDateTime convertTime, ImageConverter converter) {
        final long t1 = currentTimeMillis();
        try {
            converter.convertToJPG(srcImage, destImage);
            return Counts.append("converted", 1).since(t1);
        } catch (Exception e) {
            createRepoLogWhenFail(convertTime, "convertToJPG", "path", srcImage.getPath(), e);
            return Counts.failedOne().since(t1);
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
    private int createRepoLog(LocalDateTime actionTime, String actionType, String entityKey, String entityVal, String result, Object desc) {
        RepoLogItem repoLogItem = new RepoLogItem(actionTime, actionType, entityKey, entityVal, result, Objects.toString(desc));
        return repoLogItemDao.create(repoLogItem);
    }

    private int createRepoLogWhenFail(LocalDateTime actionTime, String actionType, String entityKey, String entityVal, Exception e) {
        return createRepoLog(actionTime, actionType, entityKey, entityVal, "FAIL", e.getMessage());
    }

    private int createRepoLogByRepos(LocalDateTime actionTime, String actionType, List<Repo> repos, Count afc) {
        String repoNames = repos.stream().map(Repo::getName).reduce((s, a) -> s + ", " + a).orElse("");
        return createRepoLog(actionTime, actionType, "repoNames", repoNames, "OK", afc.toString());
    }

    public byte[] getMediaFileContent(String hash, String cacheCategory) throws IOException {
        if (cacheCategory != null) {
            Path p = Paths.get(getHerdLocalThumbnailRepoPath(),
                    cacheCategory, hash + "." + MediaType.JPEG.getSuffix());
            if (Files.isRegularFile(p)) {
                logger.debug("read file from CACHE: {}", p.toString());
                return Files.readAllBytes(p);
            }
        }
        List<String> pathStrs = mediaPathDao.listPathsByHash(hash);
        for (String pathStr : pathStrs) {
            Path p = Paths.get(pathStr);
            if (Files.exists(p)) {
                logger.debug("read file from ORIGIN: {}", p.toString());
                return Files.readAllBytes(p);
            }
        }
        return null;
    }

}

package party.threebody.herd.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.herd.dao.*;
import party.threebody.herd.domain.*;
import party.threebody.herd.util.ImageMetaUtils;
import party.threebody.herd.util.RepoFileTypeUtils;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.AffectCounter;
import party.threebody.skean.util.DateTimeFormatters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.System.currentTimeMillis;

@Service
public class HerdService {

    private static final Logger logger = LoggerFactory.getLogger(HerdService.class);
    @Autowired
    RepoDao repoDao;
    @Autowired
    RepoFileDao repofileDao;
    @Autowired
    RepoFilePathDao repoFilePathDao;
    @Autowired
    RepoLogItemDao repoLogItemDao;
    @Autowired
    ImageInfoDao imageInfoDao;

    public List<Repo> listAliveRepos() {
        return repoDao.listByState("A");
    }


    public byte[] getRepoFileBytes(String hash) throws IOException {
        RepoFile one = repofileDao.readOne(hash);
        return Files.readAllBytes(Paths.get(one.getAbsPath()));
    }

    /**
     * scan and hash all files in all repos, and record hash and path
     *
     * @return repoFilePath AffectCount
     */
    public AffectCount scanAndHashFilesOfRepos(List<Repo> repos, LocalDateTime startTime) {
        AtomicInteger totalFiles = new AtomicInteger();
        AtomicLong totalSizeInBytes = new AtomicLong();
        List<RepoFilePath> repoFilePaths=new ArrayList<>();
        repos.forEach(repo -> {
            logger.info("scanning repo[{}] ...", repo.getName());
            Path pRoot = Paths.get(repo.getAbsPath());
            try {
                final int numFiles = (int) Files.walk(pRoot).filter(Files::isRegularFile).count();
                totalFiles.addAndGet(numFiles);
                AtomicInteger i = new AtomicInteger();
                Set<String> hashs = new HashSet<>();
                Files.walk(pRoot).filter(Files::isRegularFile).forEach(path -> {
                    long t1 = currentTimeMillis();
                    String hash = null;
                    try {
                        hash = DigestUtils.sha1Hex(Files.newInputStream(path));
                        totalSizeInBytes.addAndGet(Files.size(path));
                        repoFilePaths.add(new RepoFilePath(hash, path.toString(), null, repo.getName()));
                        logger.info("[{}/{}] hashed, {}ms used : {} << {}",
                                i.incrementAndGet(), numFiles, System.currentTimeMillis() - t1, hash, path);
                    } catch (IOException e) {
                        createRepoLogItem(startTime, "HASH", "absPath", path.toString(), "FAIL", e.getMessage());
                        logger.warn("[{}/{}] hash failed! {}ms used : {} ",
                                i.incrementAndGet(), numFiles, System.currentTimeMillis() - t1,  e.getMessage());
                    }
                });
            } catch (IOException e) {
                logger.warn("fail to scan repo: " + repo.getName(), e);
            }
        });
        logger.info("scanning finished: {} files found; {} bytes in all files.",
                totalFiles.get(), totalSizeInBytes.get());

        logger.debug("deleting all repo file paths...");
        int rnd = repoFilePathDao.deleteAll();
        AffectCounter rfpCounter = new AffectCounter();
        rfpCounter.addAndGet(AffectCount.ofOnlyDeleted(rnd));
        int rnc=repoFilePaths.stream().map(repoFilePathDao::create).reduce((s,a)->s+a).orElse(0);
        AffectCount res=new AffectCount(rnc,0,rnd,repoFilePaths.size()-rnc,0).tillNow(startTime);

        logger.info(res.toString("repoFilePath"));
        return res;
    }


    /**
     * @param syncMode
     * @return
     */
    @Transactional
    public AffectCount trackAllRepos(SyncMode syncMode) {
        List<Repo> repos = listAliveRepos();
        LocalDateTime startTime = LocalDateTime.now();
        scanAndHashFilesOfRepos(repos, startTime);
        List<RepoFilePathDao.RepoFilePathGroupByHash> rfpgs = repoFilePathDao.groupByHash();
        AtomicInteger i = new AtomicInteger();
        int rnd=repofileDao.deleteAll();
        AffectCount count = rfpgs.stream()
                .map(rfpg -> {
                    AffectCount afc = trackRepoFile(rfpg.getHash(),rfpg.getPath0(), startTime, syncMode);
                    logger.debug("[{}/{}] {}", i.incrementAndGet(), rfpgs.size(), afc.toString("file"));
                    return afc;
                })
                .reduce(AffectCount::add)
                .orElse(AffectCount.NOTHING)
                .add(AffectCount.ofOnlyDeleted(rnd));

        logger.info("tracking finished. {}", count.toString("RepoFile"));
        return count;
    }


    @Transactional
    public AffectCount trackRepoFile(String hash,String pathStr,LocalDateTime startTime, SyncMode syncMode) {
        long t1 = currentTimeMillis();
        try {
            if (!SyncMode.UPDATE.equals(syncMode)) {
                RepoFile old = repofileDao.readOne(hash);
                if (old != null) {
                    return AffectCount.NOTHING.tillNow(t1);
                }
            }
            Path path = Paths.get(pathStr);
            RepoFile repoFile = new RepoFile();
            repoFile.setHash(hash);
            repoFile.setRepoName(null);
            repoFile.setAbsPath(path.toString());
            repoFile.setTypeAndSubtype(RepoFileTypeUtils.guessRepoFileTypeByPath(path));
            repoFile.setSize((int) Files.size(path));
            repoFile.setSyncTime(LocalDateTime.now());
            repofileDao.create(repoFile);
            return AffectCount.ONE_CREATED.tillNow(t1);
        } catch (Exception e) {
            createRepoLogItem(startTime, "TRACK", "absPath", pathStr, "FAIL", e.getMessage());
            return AffectCount.ONE_FAILED.tillNow(t1);
        }
    }

    public List<RepoFile> listRepoFiles(QueryParamsSuite qps) {
        return repofileDao.readList(qps);
    }

    public int countRepoFiles(QueryParamsSuite qps) {
        return repofileDao.readCount(qps);
    }

    public AffectCount analyzeAllRepoFiles() {
        LocalDateTime analyzeTime = LocalDateTime.now();
        logger.info("analyzing all files from {} ...",  DateTimeFormatters.DEFAULT.format(analyzeTime));
        List<RepoFile> repoFiles=repofileDao.readList(null);
        int rnd=imageInfoDao.deleteAll();
        AtomicInteger i = new AtomicInteger();
        AffectCount count = repoFiles.stream()
                .map(repoFile -> {
                    AffectCount afc = analyzeRepoFile(repoFile, analyzeTime);
                    logger.debug("[{}/{}] {}", i.incrementAndGet(), repoFiles.size(), afc.toString("file"));
                    return afc;
                })
                .reduce(AffectCount::add)
                .orElse(AffectCount.NOTHING)
                .add(AffectCount.ofOnlyDeleted(rnd));
        logger.info("analyzing finished. {}", count.toString("ImageInfo"));
        return count;
    }

    /**
     * analyze the file and record its metadata
     *
     * @param repoFile
     * @return
     */
    public AffectCount analyzeRepoFile(RepoFile repoFile, LocalDateTime analyzeTime) {
        long t1 = currentTimeMillis();
        String hash = repoFile.getHash();
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(Paths.get(repoFile.getAbsPath()));
            ImageInfo imageInfo = ImageMetaUtils.parseExifInfo(inputStream);
            imageInfo.setHash(hash);
            imageInfoDao.create(imageInfo);
            return AffectCount.ONE_CREATED.tillNow(t1);
        } catch (Exception e) {
            createRepoLogItem(analyzeTime, "hash", hash, "ANALYZE", "FAIL", e.getMessage());
            return AffectCount.ONE_FAILED.tillNow(t1);
        }

    }

    int createRepoLogItem(LocalDateTime actionTime, String actionType, String entityKey, String entityVal, String result, String desc) {
        RepoLogItem repoLogItem = new RepoLogItem(actionTime, actionType, entityKey, entityVal, result, desc);
        return repoLogItemDao.create(repoLogItem);
    }


    /**
     * UPDATE: will not change already existed.
     * DROP_CREATE: drop all first and then generate all scanned.
     */
    public static enum SyncMode {
        UPDATE,
        DROP_CREATE
    }


}

package party.threebody.herd.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.herd.dao.RepoDao;
import party.threebody.herd.dao.RepoFileDao;
import party.threebody.herd.domain.Repo;
import party.threebody.herd.domain.RepoFile;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.AffectCounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
public class HerdService {

    private static final Logger logger = LoggerFactory.getLogger(HerdService.class);
    @Autowired
    RepoDao repoDao;
    @Autowired
    RepoFileDao fileDao;

    public List<Repo> listRepos() {
        return repoDao.readList(null);
    }

    @Transactional
    public AffectCount syncRepos() {
        List<Repo> repos = listRepos();
        return repos.stream().map(this::syncRepo).reduce(AffectCount::add).orElse(AffectCount.NOTHING);
    }

    public byte[] getRepoFileBytes(String hash) throws IOException {
        RepoFile one=fileDao.readOne(hash);
        return Files.readAllBytes(Paths.get(one.getAbsPath()));
    }

    public long peekRepos() {
        List<Repo> repos = listRepos();
        AtomicLong x = new AtomicLong();
        repos.forEach(repo -> {
            Path pRoot = Paths.get(repo.getAbsPath());

            try {
                final long total = Files.walk(pRoot).filter(Files::isRegularFile).count();


                AtomicInteger cnt = new AtomicInteger();
                Set<String> hashs = new HashSet<>();
                Files.walk(pRoot).filter(Files::isRegularFile).forEach(path -> {
                    String hash = null;
                    try {
                        hash = DigestUtils.sha1Hex(Files.newInputStream(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    hashs.add(hash);
                    logger.info("[{}/{}] >> {}", cnt.incrementAndGet(), total, path);

                });
                logger.info("hashs size: {}", hashs.size());
                logger.debug("files walked: {}", total);
                x.addAndGet(total);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return x.get();
    }


    @Transactional
    public AffectCount syncRepo(Repo repo) {
        logger.info("synchronising repo[{}]...", repo.getName());
        Path pRoot = Paths.get(repo.getAbsPath());
        final AffectCounter counter = new AffectCounter();
        Stream<Path> fileStream1 = null, fileStream2 = null;
        try {

            fileStream1 = Files.walk(pRoot).filter(Files::isRegularFile);
            fileStream2 = Files.walk(pRoot).filter(Files::isRegularFile);
        } catch (IOException e) {
            logger.warn("fetch repo[" + pRoot + "] failed. ", e);
        }
        final int numRepos = (int) fileStream1.count();
        fileStream2.forEach(path -> {
            AffectCount afc = syncRepoFile(repo, path);
            counter.addAndGet(afc);
            logger.debug("[{}/{}] {}", counter.result().total(), numRepos, afc.toString("file"));
        });
        logger.info("sync repo[{}] done: '{}' ", repo.getName(), repo.getAbsPath());
        logger.info("{}", counter.result().toString("file"));

        return counter.result();
    }

    public AffectCount syncRepoFile(Repo repo, Path path) {
        long t1 = System.currentTimeMillis();
        RepoFile repoFile = new RepoFile();
        try {
            String hash = DigestUtils.sha1Hex(Files.newInputStream(path));
            repoFile.setHash(hash);
            repoFile.setRepoName(repo.getName());
            repoFile.setAbsPath(path.toString());
            repoFile.setSize((int) Files.size(path));
            repoFile.setSyncTime(LocalDateTime.now());
            RepoFile old = fileDao.readOne(repoFile.getHash());
            if (old != null) {
                return AffectCount.ofOnlyUpdated(fileDao.update(repoFile, repoFile.getHash())).tillNow(t1);
            } else {
                fileDao.create(repoFile);
                return AffectCount.ONE_CREATED.tillNow(t1);
            }
        } catch (IOException e) {
            logger.warn("sync repo file failed. ", e);
            return AffectCount.ONE_FAILED.tillNow(t1);
        }


    }

    public List<RepoFile> listRepoFiles(QueryParamsSuite qps) {
        return fileDao.readList(qps);
    }

    public int countRepoFiles(QueryParamsSuite qps) {
        return fileDao.readCount(qps);
    }

}

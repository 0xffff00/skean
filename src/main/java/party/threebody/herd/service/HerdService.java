package party.threebody.herd.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import party.threebody.herd.dao.HerdCatalogDao;
import party.threebody.herd.dao.HerdFileDao;
import party.threebody.herd.domain.HerdCatalog;
import party.threebody.herd.domain.HerdFile;
import party.threebody.skean.mvc.generic.AffectCounter;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.generic.AffectCount;

@Service
public class HerdService {

	static final Logger logger = LoggerFactory.getLogger(HerdService.class);
	@Autowired
	HerdCatalogDao catalogDao;
	@Autowired
	HerdFileDao fileDao;

	public List<HerdCatalog> listCatalogs() {
		return catalogDao.list();
	}

	@Transactional
	public AffectCount syncCatalogs() {
		List<HerdCatalog> catalogs = listCatalogs();
		return catalogs.stream().map(this::syncCatalog).reduce(AffectCount::add).orElse(AffectCount.NOTHING);
	}

	@Transactional
	public AffectCount syncCatalog(HerdCatalog catalog) {
		logger.info("synchronising catalog[{}]...", catalog.getName());
		Path pRoot = Paths.get(catalog.getLocalPath());
		final AffectCounter counter = new AffectCounter();
		try {

			Files.walk(pRoot).filter(Files::isRegularFile).forEach(path -> {
				try {
					String hash = DigestUtils.sha1Hex(Files.newInputStream(path));
					long fileSize = Files.size(path);
					String relPath = pRoot.relativize(path).toString();
					HerdFile herdFile = new HerdFile(hash, catalog.getName(), relPath, (int) fileSize);
					counter.addAndGet(syncHerdFile(herdFile));
				} catch (IOException e) {
					logger.warn("unreadable path: " + path, e);
				}

			});
			logger.info("sync Catalog[{}] done: '{}' ", catalog.getName(), catalog.getLocalPath());
			logger.info("{}", counter);
		} catch (IOException e) {
			logger.warn("sync catalog failed. ", e);
		}
		return counter.result();
	}

	public AffectCount syncHerdFile(HerdFile herdFile) {
		herdFile.setSyncTime(LocalDateTime.now());
		HerdFile old = fileDao.readOne(herdFile.getHash());
		if (old != null) {
			return AffectCount.ofOnlyUpdated(fileDao.update(herdFile));
		} else {
			fileDao.create(herdFile);
			return AffectCount.ONE_CREATED;
		}
	}
	
	public List<HerdFile> listHerdFiles(QueryParamsSuite qps){
		return fileDao.readList(qps);
	}
	
	public int countHerdFiles(QueryParamsSuite qps){
		return fileDao.readCount(qps);
	}
	
}

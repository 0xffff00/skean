package party.threebody.herd.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
	public void syncCatalogs() {
		List<HerdCatalog> catalogs = listCatalogs();
		catalogs.forEach(this::syncCatalog);
	}

	@Transactional
	public void syncCatalog(HerdCatalog catalog) {
		logger.info("synchronising catalog[{}]...", catalog.getName());
		Path pRoot = Paths.get(catalog.getLocalPath());
		
		try {
			final AtomicInteger count = new AtomicInteger();
			Files.walk(pRoot).filter(Files::isRegularFile).forEach(path -> {
				try {
					String hash = DigestUtils.sha1Hex(Files.newInputStream(path));
					long fileSize = Files.size(path);
					String relPath = pRoot.relativize(path).toString();
					HerdFile herdFile = new HerdFile(hash, catalog.getName(), relPath, (int) fileSize);
					count.incrementAndGet();
					syncFile(herdFile);
				} catch (IOException e) {
					logger.warn("unreadable path: " + path, e);
				}

			});
			logger.info("{} files' place info synchronised. ",count);
		} catch (IOException e) {
			logger.warn("sync catalog failed. ", e);
		}

	}

	public void syncFile(HerdFile herdFile) {
		herdFile.setSyncTime(LocalDateTime.now());
		HerdFile old = fileDao.readOne(herdFile.getHash());
		if (old != null) {
			fileDao.update(herdFile);
		} else {
			fileDao.create(herdFile);
		}
	}
}

package party.threebody.herd.dao

import java.time.LocalDateTime
import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.herd.domain.HerdFile
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.mvc.generic.AbstractCrudDAO

@Repository
class HerdFileDao extends AbstractCrudDAO<HerdFile,String>{

	@Autowired ChainedJdbcTemplate cjt

	@Override
	protected String getTable() {
		'hrd_file'
	}

	@Override
	protected Class<HerdFile> getBeanClass() {
		HerdFile.class
	}

	@Override
	protected List<String> getPrimaryKeyColumns() {
		['hash']
	}

	@Override
	protected List<String> getAffectedColumns() {
		null
	}

	int create(String hash,String catalog,	String relPath,	int fileSize){
		HerdFile hf=new HerdFile(hash:hash,catalog:catalog,relPath:relPath,fileSize:fileSize,syncTime:LocalDateTime.now());
		create(hf)
	}

	@Override
	public HerdFile create(HerdFile e) {
		cjt.from(getTable()).affect([
			hash:e.hash,
			catalog:e.catalog,
			rel_path:e.relPath,
			file_size:e.fileSize,
			sync_time:LocalDateTime.now()
		]).insert()
		null
	}

	public int update(HerdFile e) {
		cjt.from(getTable()).affect([
			hash:e.hash,
			catalog:e.catalog,
			rel_path:e.relPath,
			file_size:e.fileSize,
			sync_time:LocalDateTime.now()
		]).by([hash:e.hash]).update()
	}
}

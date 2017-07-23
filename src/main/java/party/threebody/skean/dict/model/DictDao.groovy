package party.threebody.skean.dict.model

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Repository
class DictDao {
	
	@Autowired ChainedJdbcTemplate cjt
	
	String getAliasRoot(String alias){
		cjt.from("dct_rel_sp_alias").select('key').by("val").val(alias).limit(1).firstCell()
	}
	
	
}

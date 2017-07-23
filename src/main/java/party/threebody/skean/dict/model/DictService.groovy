package party.threebody.skean.dict.model

import org.apache.commons.collections4.MultiValuedMap
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.SingleColumnRowMapper
import org.springframework.stereotype.Service

import groovy.transform.ToString
import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Service
class DictService {
	@Autowired DictDao dictDao
	@Autowired ChainedJdbcTemplate cjt
	
	String getFormal(String alias){
		dictDao.getAliasRoot(alias) || alias
	}
	
	List<Word> getWordA(String text){
		
		cjt.from("dct_rel_sp_alias").by("key").val(text).list().collect{it};
	}
	
	
	
	
	 
}

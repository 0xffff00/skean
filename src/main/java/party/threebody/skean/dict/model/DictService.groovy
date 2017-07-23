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

	@Autowired ChainedJdbcTemplate cjt
	
	
	
//	List<Noun> listNouns(){
//		cjt.from("dct_noun").list().collect{it};
//	}
	
	
	
	
	 
}

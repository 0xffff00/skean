package party.threebody.skean.dict.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Repository
class DictDao {
	@Autowired ChainedJdbcTemplate cjt
	
	
	
}

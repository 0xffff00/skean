package party.threebody.skean.id.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.skean.id.model.UserPO
import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Repository
class UserDAO {

	@Autowired ChainedJdbcTemplate cjt
	
	List<UserPO> list(){
		cjt.from('id_user').by('state').val('A').list(UserPO.class)
	}

	
}

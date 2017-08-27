package party.threebody.skean.id.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import party.threebody.skean.id.model.UserPO;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

import java.util.List;

@Repository
public class UserDAO  {

	@Autowired
	private ChainedJdbcTemplate cjt;

	public List<UserPO> list() {
		return cjt.from("id_user").by("state").val("A").list(UserPO.class);
	}

}

package party.threebody.skean.id;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import party.threebody.skean.id.model.UserPO;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao{

	@Resource
	private JdbcTemplate jt;

	public List<Map<String,Object>> list() {
		return jt.queryForList("select * from sys_id_user");
	}

	public List<UserPO> listUsers() {
		return jt.queryForList("select * from sys_id_user", UserPO.class);
	}

	public UserPO getUser1(String name) {
		return jt.queryForObject("select * from sys_id_user where name=?", new RowMapper<UserPO>() {
			@Override
			public UserPO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserPO res = new UserPO();
				res.setName(rs.getString("NAME"));
				res.setPsd(rs.getString("PSD"));
				return ((UserPO) (res));
			}

		}, name);
	}

	public UserPO getUser(String name) {
		List<Map<String, Object>> list = jt.queryForList("select * from sys_id_user where name=?", name);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return fromMap(list.get(0));
	}

	public UserPO fromMap(Map<String,Object> m) {
		UserPO o = new UserPO();
		o.setName((String)m.get("NAME"));
		o.setPsd((String)m.get("PSD"));
		o.setName_disp((String)m.get("name_disp"));
		return o;
	}

	public UserPO getUserByNameContaining(String arg0) {
		return jt.queryForObject("select * form sys_id_user where name like \'%" + arg0 + "%\'", UserPO.class);
	}

	public int createUser(String name, String name_disp, String psd) {
		return jt.update("insert into sys_id_user(name,name_disp,psd) values(?,?,?)", name, name_disp, psd);
	}


}

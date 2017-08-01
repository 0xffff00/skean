package party.threebody.skean.id

import java.sql.ResultSet
import java.sql.SQLException

import javax.annotation.Resource

import org.apache.commons.collections4.CollectionUtils
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import party.threebody.skean.id.model.UserPO

@Repository
class UserDao {

	@Resource
	JdbcTemplate jt
	
	List<Map> list(){
		jt.queryForList("select * from sys_id_user")
	}
	List<UserPO> listUsers(){
		jt.queryForList("select * from sys_id_user",UserPO.class)
	}
	
	UserPO getUser1(String name){
		jt.queryForObject("select * from sys_id_user where name=?",new RowMapper<UserPO>(){
			@Override
			UserPO mapRow(ResultSet rs, int rowNum) throws SQLException{
				def res=new UserPO()
				res.setName(rs.getString("NAME"))
				res.setPsd(rs.getString("PSD"))
				res
			}
		},name)
	}
	
	UserPO getUser(String name){
		def list=jt.queryForList('select * from sys_id_user where name=?',name)
		if (CollectionUtils.isEmpty(list)){
			return null
		}
		return fromMap(list.get(0))
	}
	
	UserPO fromMap(Map m){
		new UserPO(name:m['NAME'],psd:m['PSD'],name_disp:m['name_disp'])
	}
	
	UserPO getUserByNameContaining(String arg0){
		jt.queryForObject("select * form sys_id_user where name like '%$arg0%'",UserPO.class)
	}
	
	int createUser(String name,String name_disp,String psd){
		jt.update("insert into sys_id_user(name,name_disp,psd) values(?,?,?)",name, name_disp, psd)
	}
	
}

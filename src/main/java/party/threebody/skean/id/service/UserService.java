package party.threebody.skean.id.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import party.threebody.skean.id.UserDao;
import party.threebody.skean.id.model.UserPO;




@Service
class UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	
	@Autowired
	UserDao userDao;
	
	List<Map> list(){
		return userDao.list();
	}
	List<UserPO> listUsers(){
		return userDao.listUsers();
	}
	
	UserPO getUser(String name){
		return userDao.getUser(name);
	}
	
	
	public int createUser(String name,String nameDisp,String password) throws Exception {
		UserPO u0=userDao.getUser(name);
		if (u0!=null){
			throw new Exception("Duplicate username");
		}
		String psd=passwordEncoder.encode(password);
		//def u1=new UserPo(name:name,name_disp: nameDisp,psd: psd)
		return userDao.createUser(name,nameDisp,psd);
	
	}
	
}

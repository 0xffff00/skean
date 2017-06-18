package party.threebody.skean.id

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service




@Service
class UserService {

	@Autowired
	PasswordEncoder passwordEncoder

	
	@Autowired
	UserDao userDao
	
	List<Map> list(){
		userDao.list()
	}
	List<UserPO> listUsers(){
		userDao.listUsers()
	}
	
	UserPO getUser(String name){
		userDao.getUser(name)
	}
	
	
	public int createUser(String name,String nameDisp,String password) throws Exception {
		def u0=userDao.getUser(name)
		if (u0){
			throw new Exception("Duplicate username")
		}
		def psd=passwordEncoder.encode(password)
		//def u1=new UserPo(name:name,name_disp: nameDisp,psd: psd)
		userDao.createUser(name,nameDisp,psd)
	
	}
	
}

/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.id.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import party.threebody.skean.id.UserDao;
import party.threebody.skean.id.model.UserPO;

import java.util.List;
import java.util.Map;


@Service
class UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	
	@Autowired
	UserDao userDao;
	

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

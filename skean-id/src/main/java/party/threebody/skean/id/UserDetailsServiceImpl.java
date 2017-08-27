package party.threebody.skean.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import party.threebody.skean.id.model.UserPO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPO u = userDao.getUser(username);
		if (u == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails res = User.withUsername(username).password(u.getPsd()).authorities("aaa","bbb").build();
		return res;
	}

}

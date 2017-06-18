package party.threebody.s4g.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import party.threebody.skean.id.UserService


@Controller
@RequestMapping("/user")
public class UserAccessController {

	@Autowired
	UserService userService
	
	@ResponseBody
	@RequestMapping("/login")
	public String user(String username,String password){
		
	}
	
}

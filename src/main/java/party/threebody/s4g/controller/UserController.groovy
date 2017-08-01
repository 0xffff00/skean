package party.threebody.s4g.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import party.threebody.skean.id.service.UserService
import party.threebody.skean.id.model.UserPO



@RestController
//@RequestMapping(produces="application/json; charset=UTF-8")
public class UserController {

	@Autowired
	UserService userService
	
	@RequestMapping("/users")
	public List<Map> users(){
		userService.list()
	}
	
	@RequestMapping("/user/list")
	public List<Map> userls(){
		userService.list()
	}
	
	@RequestMapping("/user/byName/{name}")
	public UserPO user(@PathVariable String name){
		userService.getUser(name)
	}
	
	@PreAuthorize("hasPermission('sys.man.user','create')")
	@PostMapping("/user/create.json")
	public String create(String name,String nameDisp,String password){
		userService.createUser(name,nameDisp,password)
		"haha"
	}
}

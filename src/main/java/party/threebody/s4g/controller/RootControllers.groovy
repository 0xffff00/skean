package party.threebody.s4g.controller

import java.time.LocalDateTime

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

import party.threebody.skean.id.UserService


@Controller
public class RootController {

	@Autowired
	UserService userService

	@RequestMapping("/about")
	public ModelAndView about(){

		String str=LocalDateTime.now()
		new ModelAndView().addObject('ver',str)
	}
	
	//for 403 access denied page
	@RequestMapping("/403")
	public ModelAndView accesssDenied(@AuthenticationPrincipal UserDetails currentUser) {
		ModelAndView model = new ModelAndView();
		if (currentUser){
			model.addObject("username", currentUser.getUsername());
		}		
		//model.setViewName("403");
		return model;

	}
}

package party.threebody.skean.id.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")

public class UserController {
	@GetMapping("/a")
	public String g(@MatrixVariable(required=false,name="key") String key) {
		return key;
	}
}

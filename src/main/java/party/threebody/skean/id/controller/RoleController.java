package party.threebody.skean.id.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.skean.id.model.Role;
import party.threebody.skean.id.service.RoleService;
import party.threebody.skean.mvc.generic.SimpleBeanCrudRestController;
import party.threebody.skean.mvc.generic.SingleBeanCrudService;


@RequestMapping("/id/roles")
@RestController
public class RoleController extends SimpleBeanCrudRestController<Role, String>{

	@Autowired
	RoleService roleService;
	

	@Override
	protected SingleBeanCrudService<Role, String> getSingleBeanCrudService() {
		return roleService;
	}
	
}

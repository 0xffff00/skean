package party.threebody.skean.id.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.skean.id.dao.RoleDAO;
import party.threebody.skean.id.model.Role;
import party.threebody.skean.mvc.generic.GenericCrudDaoTemplateService;
import party.threebody.skean.mvc.generic.SimpleCrudRestConfig;
import party.threebody.skean.mvc.generic.SimpleCrudRestController;

@RequestMapping("/id/roles")
@RestController
public class RoleController extends SimpleCrudRestController<Role, String> {

//	@Autowired 
//	RoleDAO roleDAO1;
	@Autowired
	GenericCrudDaoTemplateService daoTemplateService;

	@Autowired 
	public RoleController(RoleDAO roleDAO1){
		setConfig(new SimpleCrudRestConfig<Role, String>(roleDAO1));
	}

}

package party.threebody.skean.id.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.id.dao.RoleDAO;
import party.threebody.skean.id.model.Role;
import party.threebody.skean.mvc.dao.SinglePKCrudDAO;
import party.threebody.skean.mvc.generic.SimpleDaoCrudRestController;

@RequestMapping("/id/roles")
@RestController
public class RoleController extends SimpleDaoCrudRestController<Role, String> {

	@Autowired
	RoleDAO roleDAO;

	@Override
	protected SinglePKCrudDAO<Role, String> getCrudDAO() {
		return roleDAO;
	}

}

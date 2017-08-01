package party.threebody.skean.id.service;

import org.springframework.stereotype.Service;

import party.threebody.skean.id.model.Role;
import party.threebody.skean.mvc.generic.SimpleBeanCrudService;

@Service
public class RoleService extends SimpleBeanCrudService<Role,String>{

	@Override
	protected String getTable() {
		return "id_role";
	}

	@Override
	protected Class<Role> getBeanClass() {
		return Role.class;
	}

	@Override
	protected String[] getPkCol() {
		return new String[]{"name"};
	}

	
}

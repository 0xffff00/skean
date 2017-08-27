package party.threebody.skean.id.dao;

import org.springframework.stereotype.Repository;
import party.threebody.skean.id.model.Role;
import party.threebody.skean.mvc.generic.AbstractCrudDAO;

import java.util.Arrays;
import java.util.List;

@Repository
public class RoleDAO extends AbstractCrudDAO<Role, String> {

	@Override
	protected String getTable() {
		return "id_role";
	}

	@Override
	protected Class<Role> getBeanClass() {
		return Role.class;
	}

	@Override
	protected List<String> getPrimaryKeyColumns() {
		return Arrays.asList("name");
	}

	@Override
	protected List<String> getAffectedColumns() {
		return null;
	}

}

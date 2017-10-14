package party.threebody.skean.id.dao;

import org.springframework.stereotype.Repository;
import party.threebody.skean.id.model.Role;
import party.threebody.skean.web.mvc.dao.SinglePKCrudDAO;

import java.util.List;

@Repository
public class RoleDAO extends SinglePKCrudDAO<Role, String> {

    @Override
    protected String getTable() {
        return "id_role";
    }

    @Override
    protected Class<Role> getBeanClass() {
        return Role.class;
    }

    @Override
    protected String getPrimaryKeyColumn() {
        return "name";
    }

    @Override
    protected List<String> getAffectedColumns() {
        return null;
    }

}

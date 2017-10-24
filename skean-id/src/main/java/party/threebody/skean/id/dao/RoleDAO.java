package party.threebody.skean.id.dao;

import org.springframework.stereotype.Repository;
import party.threebody.skean.id.model.Role;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO;

@Repository
public class RoleDAO extends SinglePKJpaCrudDAO<Role, String> {


    @Override
    public ChainedJdbcTemplate getChainedJdbcTemplate() {
        return null;
    }
}

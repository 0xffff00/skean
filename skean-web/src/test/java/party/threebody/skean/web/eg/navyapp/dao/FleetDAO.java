package party.threebody.skean.web.eg.navyapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.web.eg.navyapp.domain.Fleet;
import party.threebody.skean.web.mvc.dao.DualPKJpaCrudDAO;

@Repository
public class FleetDAO extends DualPKJpaCrudDAO<Fleet,String,String> {

    @Autowired ChainedJdbcTemplate cjt;
    @Override
    public ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt;
    }


}

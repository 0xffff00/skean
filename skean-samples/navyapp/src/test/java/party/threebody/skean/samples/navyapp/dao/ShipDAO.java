package party.threebody.skean.samples.navyapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.samples.navyapp.domain.Ship;
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO;

@Repository
public class ShipDAO extends SinglePKJpaCrudDAO<Ship,String> {
    @Autowired ChainedJdbcTemplate cjt;

    @Override
    public ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt;
    }
}

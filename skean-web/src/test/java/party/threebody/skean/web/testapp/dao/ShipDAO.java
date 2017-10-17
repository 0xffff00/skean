package party.threebody.skean.web.testapp.dao;

import org.springframework.stereotype.Repository;
import party.threebody.skean.web.testapp.domain.Ship;
import party.threebody.skean.web.testapp.mvc.dao.SinglePKCrudDAO;

import java.util.List;

@Repository
public class ShipDAO extends SinglePKCrudDAO<Ship,String>{

    @Override
    protected String getPrimaryKeyColumn() {
        return "code";
    }

    @Override
    protected String getTable() {
        return "navy_ship";
    }

    @Override
    protected Class<Ship> getBeanClass() {
        return Ship.class;
    }

    @Override
    protected List<String> getAffectedColumns() {
        return null;
    }
}

package party.threebody.skean.web.eg.navyapp.dao;

import org.springframework.stereotype.Repository;
import party.threebody.skean.web.eg.navyapp.domain.Ship0;
import party.threebody.skean.web.mvc.dao0.SinglePKCrudDAO;

import java.util.List;

@Repository
public class Ship0DAO extends SinglePKCrudDAO<Ship0,String> {

    @Override
    protected String getPrimaryKeyColumn() {
        return "code";
    }

    @Override
    protected String getTable() {
        return "navy_ship";
    }

    @Override
    protected Class<Ship0> getEntityClass() {
        return Ship0.class;
    }

    @Override
    protected List<String> getAffectedColumns() {
        return null;
    }
}

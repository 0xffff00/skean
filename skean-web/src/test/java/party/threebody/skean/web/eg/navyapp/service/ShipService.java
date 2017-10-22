package party.threebody.skean.web.eg.navyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.web.eg.navyapp.dao.Ship0DAO;
import party.threebody.skean.web.eg.navyapp.domain.Ship0;

import java.util.List;
import java.util.Map;

@Service
public class ShipService {

    @Autowired
    Ship0DAO shipDAO;

    public Ship0 getShip(String code) {
        return shipDAO.readOne(code);
    }

    public List<Ship0> listShips(QueryParamsSuite qps) {
        return shipDAO.readList(qps);
    }

    public int countShips(QueryParamsSuite qps) {
        return shipDAO.readCount(qps);
    }

    public Ship0 createAndGet(Ship0 ship) {
        return shipDAO.createAndGet(ship);
    }

    public int update(Ship0 ship, String code) {
        return shipDAO.update(ship, code);
    }

    public int partialUpdate(Map<String, Object> fieldsToUpdate, String code) {
        return shipDAO.partialUpdate(fieldsToUpdate, code);
    }

    public int delete(String code) {
        return shipDAO.delete(code);
    }

}

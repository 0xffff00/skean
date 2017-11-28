package party.threebody.skean.web.eg.navyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.web.eg.navyapp.dao.ShipDAO;
import party.threebody.skean.web.eg.navyapp.domain.Ship;

import java.util.List;
import java.util.Map;

@Service
public class ShipService {

    @Autowired
    ShipDAO shipDAO;

    public Ship getShip(String code) {
        return shipDAO.readOne(code);
    }

    public List<Ship> listShips(CriteriaAndSortingAndPaging qps) {
        return shipDAO.readList(qps);
    }

    public int countShips(Criteria qps) {
        return shipDAO.readCount(qps);
    }

    public Ship createAndGet(Ship ship) {
        return shipDAO.createAndGet(ship);
    }

    public int update(Ship ship, String code) {
        return shipDAO.update(ship, code);
    }

    public int partialUpdate(Map<String, Object> fieldsToUpdate, String code) {
        return shipDAO.partialUpdate(fieldsToUpdate, code);
    }

    public int delete(String code) {
        return shipDAO.delete(code);
    }

    public int deleteAll(){
        return shipDAO.deleteSome(null);
    }

}

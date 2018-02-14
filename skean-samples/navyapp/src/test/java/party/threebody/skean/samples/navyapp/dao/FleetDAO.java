package party.threebody.skean.samples.navyapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.samples.navyapp.domain.Fleet;
import party.threebody.skean.samples.navyapp.domain.Ship;
import party.threebody.skean.web.mvc.dao.DualPKsJpaCrudDAO;

import java.util.List;

@Repository
public class FleetDAO extends DualPKsJpaCrudDAO<Fleet, String, String> {

    @Autowired ChainedJdbcTemplate cjt;

    @Override
    public ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt;
    }


    public List<Ship> listShipsByFleet(String countryCode,String fleetCode){
        String sql="SELECT s.* FROM navy_ships s JOIN navy_fleet_ship fs ON s.code=fs.ship_code"
        +" WHERE fs.country_code=? AND fs.fleet_code=?";
        return cjt.sql(sql).arg(countryCode,fleetCode).list(Ship.class);
    }

    public int addShipToFleet(String countryCode,String fleetCode,String shipCode){
        String sql="INSERT INTO navy_fleet_ship(country_code,fleet_code,ship_code) VALUES(?,?,?)";
        return cjt.sql(sql).arg(countryCode,fleetCode,shipCode).execute();
    }

}

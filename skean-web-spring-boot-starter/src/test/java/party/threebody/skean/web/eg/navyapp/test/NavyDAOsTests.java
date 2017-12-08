package party.threebody.skean.web.eg.navyapp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.collections.Maps;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.web.eg.navyapp.dao.FleetDAO;
import party.threebody.skean.web.eg.navyapp.dao.ShipDAO;
import party.threebody.skean.web.eg.navyapp.domain.Fleet;
import party.threebody.skean.web.eg.navyapp.domain.Ship;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NavyDAOsTests {

    @Autowired ShipDAO shipDAO;
    @Autowired FleetDAO fleetDAO;

    @Test
    public void testShipDAO() throws Exception {
        //delete all
        shipDAO.deleteSome(Criteria.ALLOW_ALL);
        //create
        shipDAO.create(new Ship("CA01", "Taishan", 31180, 2020));
        shipDAO.create(new Ship("CA02", "Hengshan", 32500, 2024));
        shipDAO.create(new Ship("CA03", "Songshan", 32840, 2026));
        shipDAO.create(new Ship("CA04", "Huashan", 32850, 2026));
        assertEquals(4, shipDAO.readCount(null));
        Ship ca01 = shipDAO.readOne("CA01");
        assertNotNull(ca01.getCreateTime());
        LocalDateTime t1 = ca01.getUpdateTime();
        assertNotNull(t1);

        //update
        shipDAO.partialUpdate(Maps.of("weight", 31200), "CA01");
        ca01 = shipDAO.readOne("CA01");
        assertEquals(31200, (int) ca01.getWeight());
        LocalDateTime t2 = ca01.getUpdateTime();
        assertNotNull(ca01.getCreateTime());
        assertNotNull(t2);
        assertTrue(t2.isAfter(t1));

        shipDAO.update(new Ship("CA02a", "Hengshan1", 32501, 2024), "CA02");
        Ship ca02 = shipDAO.readOne("CA02a");
        assertEquals(32501, (int) ca02.getWeight());
        t2 = ca02.getUpdateTime();
        assertTrue(t2.isAfter(t1));

        //delete
        shipDAO.delete("CA04");
        assertEquals(3, shipDAO.readCount(null));
        assertEquals(2026, (int) shipDAO.readOne("CA03").getBirthYear());

        //clear
        shipDAO.deleteSome(Criteria.ALLOW_ALL);
        assertEquals(0, shipDAO.readCount(Criteria.ALLOW_ALL));


    }

    @Test
    public void testFleetDAO() throws Exception {
        fleetDAO.create(new Fleet("CHN", "SOUTH1", "South No.1 Fleet", "Zhangzz", null));
        fleetDAO.create(new Fleet("CHN", "SOUTH2", "South No.2 Fleet", "Zhangzz", null));
        assertEquals(2, fleetDAO.readCount(null));
        assertEquals(2, fleetDAO.readCount(Criteria.ALLOW_ALL));
        assertEquals(0, fleetDAO.readCount(Criteria.DENY_ALL));
        assertEquals(1, fleetDAO.readCount(Criteria.of(new BasicCriterion("code","SOUTH2"))));
        Fleet f2 = fleetDAO.readOne("CHN", "SOUTH2");
        LocalDateTime t1 = f2.getUpdateTime();
        assertNotNull(t1);

        fleetDAO.partialUpdate(Maps.of("code", "SOUTH20", "leaderName", "Aaaaa"), "CHN", "SOUTH2");
        f2 = fleetDAO.readOne("CHN", "SOUTH20");
        LocalDateTime t2 = f2.getUpdateTime();
        assertTrue(t2.isAfter(t1));
    }

}

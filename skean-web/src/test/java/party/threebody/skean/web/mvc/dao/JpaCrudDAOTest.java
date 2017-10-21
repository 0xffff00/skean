package party.threebody.skean.web.mvc.dao;

import org.junit.Test;
import party.threebody.skean.web.eg.navyapp.dao.ShipDAO2;

public class JpaCrudDAOTest {
    @Test
    public void getBeanClass() throws Exception {
        ShipDAO2 dao2=new ShipDAO2();

        System.out.println(dao2.getEntityClass());


    }

}
package party.threebody.skean.jdbc.eg.sk021.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.eg.sk021.domain.ShipDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ChainedJdbcReadingTest {

    @Autowired
    JdbcTemplate jdbcTmpl;
    @Autowired
    ChainedJdbcTemplate q;

    static void prt(Object a) {
        System.out.println(a);
    }

    @Test
    public void jdbcTmplTest() {
        List list = jdbcTmpl.queryForList("select * from `t1ship`");
        System.out.println(list);

    }

    @Test
    public void from_fetching_obj() {
        ShipDO ship1 = new ShipDO();
        ship1.setCode("DD32");
        ship1.setName("Alagh");
        ship1.setBirth(1985111);

        List<Map<String, Object>> res1 = q.from("t1ship").by("code").val(ship1).list();
        assertEquals(1, res1.size());
        assertEquals("DD32", res1.get(0).get("code"));
        List<ShipDO> res2 = q.from("t1ship").by("code", "weig").val("DD32", 1324).list(ShipDO.class);
        assertEquals(1, res2.size());
        assertEquals("Alagh", res2.get(0).getName());

    }

    @Test
    public void from_fetching() {
        //list,count,page
        assertNotNull(q.from("t1ship").list());
        assert q.from("t1ship").count() > 4;
        assert q.from("t1ship").select("code").count() > 4;
        assert q.from("t1ship").page(1, 3).list().size() == 3;
        assert q.from("t1ship").page(2, 4).list().size() >= 2;
        //orderBy
        assert q.from("t1ship").orderBy("code").list().get(0).get("code").equals("BB14");
        assertEquals("BB17", q.from("t1ship").where("code like 'BB%'").orderBy("code desc").list().get(0).get("code"));
        assertEquals("BB17", q.from("t1ship").where("code like 'BB%'").orderBy("!code").list().get(0).get("code"));
        assertEquals("BB17", q.from("t1ship").where("code like 'BB%'").orderBy("-code").list().get(0).get("code"));

        //by
        assert 0 < q.from("t1ship").by("code").valArr(new String[]{"BB17"}).count();
        assert 0 < q.from("t1ship").by("code").val("BB17").count();
        assert 0 < q.from("t1ship").by("weig").val((String) null).count();

        assertNull(q.from("t1ship").by("code").valMap(new HashMap()).firstCell());
        assertNotNull(q.from("t1ship").criteria().list());
        assertNotNull(q.from("t1ship").criteria(new BasicCriterion("weig", ">", 30000)).list());
        assertNotNull(q.from("t1ship").criteria(new BasicCriterion("code", "likeLeft", "CV")).list());
        assertNotNull(q.from("t1ship").criteria(new BasicCriterion("code", "like", "V0"),
                new BasicCriterion("weig", ">", 30000), new BasicCriterion("name", "!=", null)).list());

        assertNotNull(q.from("t1ship").where("code like ?").val("DD%").list());
    }


}
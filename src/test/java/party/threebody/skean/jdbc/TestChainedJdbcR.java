package party.threebody.skean.jdbc;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import party.threebody.s4g.conf.spring.RootConfig;
import party.threebody.skean.core.query.BasicCriterion;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("memdb")
public class TestChainedJdbcR {

	@Autowired
	JdbcTemplate jdbcTmpl;
	@Autowired
	ChainedJdbcTemplate q;

	static void prt(Object a) {
		System.out.println(a);
	}

	@Test
	public void jdbcTmpl() {
		List list = jdbcTmpl.queryForList("select * from `t1ship`");
		System.out.println(list);

	}

	@Test
	public void from_fetching_obj() {
		ShipDO cv06 = new ShipDO();
		cv06.setCode("CV06");
		cv06.setName("Big E");
		cv06.setBirth(1989);

		List<Map<String, Object>> res1 = q.from("t1ship").by("code").val(cv06).list();
		assert 1 == res1.size();
		assert res1.get(0).get("birth").equals(1989);
		List<ShipDO> res2 = q.from("t1ship").by("code","birth").val("CV06",1989).list(ShipDO.class);
		assert 1 == res2.size();
		assert res2.get(0).getName().equals("Big E");

	}

	@Test
	public void from_fetching() {
		//list,count,page 
		assertNotNull(q.from("t1ship").list());
		assert q.from("t1ship").count() >4;
		assert q.from("t1ship").select("code").count() > 4;
		assert q.from("t1ship").page(1, 3).list().size()==3;
		assert q.from("t1ship").page(2, 4).list().size()>=2;
		//orderBy
		assert q.from("t1ship").orderBy("code").list().get(0).get("code").equals("BB14");
		assertEquals("BB16",q.from("t1ship").where("code like 'BB%'").orderBy("code desc").list().get(0).get("code"));
		assertEquals("BB16",q.from("t1ship").where("code like 'BB%'").orderBy("!code").list().get(0).get("code"));
		assertEquals("BB16",q.from("t1ship").where("code like 'BB%'").orderBy("-code").list().get(0).get("code"));

		//by
		assert 0 < q.from("t1ship").by("code").valArr(new String[] { "BB15" }).count();
		assert 0 < q.from("t1ship").by("code").val("BB15").count();
		int x1 = q.from("t1ship").by("weig").val((String) null).count();
		assert x1 == 1;
		
		prt(q.from("t1ship").by("code").valMap(new HashMap()).firstCell());
		assertNotNull(q.from("t1ship").criteria().list());
		assertNotNull(q.from("t1ship").criteria(new BasicCriterion("weig", ">", 30000)).list());
		assertNotNull(q.from("t1ship").criteria(new BasicCriterion("code", "^", "CV")).list());
		assertNotNull(q.from("t1ship").criteria(new BasicCriterion("code", "~", "V0"),
				new BasicCriterion("weig", ">", 30000), new BasicCriterion("name", "!=", null)).list());

		assertNotNull(q.from("t1ship").where("code like ?").val("DD%").list());
	}

	

}
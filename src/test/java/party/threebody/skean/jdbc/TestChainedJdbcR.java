package party.threebody.skean.jdbc;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import party.threebody.s4g.conf.spring.RootConfig;
import party.threebody.s4g.dict.Noun;
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

	static void prt(Object a){
		System.out.println(a);
	}
	@Test
	public void jdbcTmpl() {
		List list = jdbcTmpl.queryForList("select * from `dct_noun`");
		System.out.println(list);		
		//q.from("t").criteria(null).orderBy().page(0, 0).first();
		
	}
	
	//@Test
	public void from_fetching_obj(){
		Noun n1=new Noun();
		n1.setQual("xxx");
		n1.setWord("CHN");
		
		prt(q.from("dct_noun").by("word").val(n1).list());
		prt(q.from("dct_noun").by("word").val("CHN").list(Noun.class));
		

		
	}
	@Test
	public void from_fetching(){
		assertNotNull(q.from("dct_noun").list());
		assert q.from("dct_noun").count()>10;
		assert q.from("dct_noun").select("word").count()>0;
		prt(q.from("dct_noun").page(1, 3).list());
		prt(q.from("dct_noun").page(2, 3).list());
		prt(q.from("dct_noun").select("word","lang").orderBy("word desc").list());
		prt(q.from("dct_noun").orderBy("word","type").list());
		
		assert 0<q.from("dct_noun").by("word").valArr(new String[]{"fdu"}).count();
		assert 0<q.from("dct_noun").by("word").val("fdu").count();
		int x1= q.from("dct_noun").by("type").val((String)null).count();
		assert x1>0;
		assert x1==q.from("dct_noun").by("type").val((String)null).list().size();
		assertNotNull(q.from("dct_noun").by("word","lang").val("fdu","en").list());
		prt("######t1ship#######");
		prt(q.from("t1ship").by("code").valMap(new HashMap()).firstCell());	
		assertNotNull(q.from("t1ship").criteria().list());	
		assertNotNull(q.from("t1ship").criteria(new BasicCriterion("weig",">",30000)).list());	
		assertNotNull(q.from("t1ship").criteria(new BasicCriterion("code","^","CV")).list());
		assertNotNull(q.from("t1ship").criteria(new BasicCriterion("code","~","V0"),
				new BasicCriterion("weig",">",30000),new BasicCriterion("name","!=",null)
				).list());
		
		assertNotNull(q.from("t1ship").where("code like ?").val("DD%").list());
	}
	


}

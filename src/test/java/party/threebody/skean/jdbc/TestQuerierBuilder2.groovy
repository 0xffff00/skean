package party.threebody.skean.jdbc;

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration

import party.threebody.s4g.conf.spring.RootConfig
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("memdb")
class TestQuerierBuilder2 {
	@Autowired
	JdbcTemplate jdbcTmpl;
	@Autowired
	ChainedJdbcTemplate q;
	
	@Test
	public void from_inserting(){
		assert q.from("t1ship").affect("code","weig").val("CVN07","67432").insert()>0;
		assert q.from("t1ship").affect("code","weig").val([code:"CVN08"]).insert()>0;
		assert q.from("t1ship").val([code:"CVN09",name:"HATH"]).insert()>0;
		
		assertNotNull q.from("t1ship").where("code like ?").val('CVN%').list();
	}
	@Test
	public void from_updating(){
		q.from("t1ship").affect("birth").where("1=1").val(1999).update();
		println q.from("t1ship").by("birth").val(1999).count();
		q.from("t1ship").affect("birth").by("code").val(1918,"CV06").update();
		println q.from("t1ship").by("code").val("CV06").list();
		q.from("t1ship").by("code").val([code:'CV06',name:'moha',weig:33599]).update();
		println q.from("t1ship").by("code").val("CV06").list();
	}
	
	@Test
	public void from_deleting(){
		q.from("t1ship").by("birth").val(1999).delete();
		assert q.from("t1ship").by("birth").val(1999).count()==0;
	}

}


package party.threebody.s4g.dict;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import party.threebody.s4g.conf.spring.RootConfig;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("memdb")
public class DictServiceTest2 {
	@Autowired
	DictService dictService;
	@Autowired
	ChainedJdbcTemplate cjt;
	
	@Test
	public void t1() {
		//cjt.sql("SELECT * FROM dct_rel_alias").list();
		cjt.from("dct_rel_alias").list();
		
	}


}

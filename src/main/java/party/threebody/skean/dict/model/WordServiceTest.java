package party.threebody.skean.dict.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import party.threebody.s4g.conf.spring.RootConfig;
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("~memdb")
public class WordServiceTest {
	@Autowired WordService ws;
	@Autowired DictDao dd;


	@Test
	public void testT2() {
		System.out.println(ws.t2("高校").toString(0));
		System.out.println(ws.listAttributeRelations("高校"));
		System.out.println(ws.listReferenceRelations("高校"));
	}

}

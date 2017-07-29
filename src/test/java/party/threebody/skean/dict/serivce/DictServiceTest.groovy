package party.threebody.skean.dict.serivce;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import party.threebody.s4g.conf.spring.RootConfig;
import party.threebody.skean.dict.dao.DictDao

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("~memdb")
public class DictServiceTest {
	@Autowired DictDao dd
	//@Test
	void getFormal() {
//		assertEquals "复旦大学",ds.getFormal("复旦大学") 
//		assertEquals "复旦大学",ds.getFormal("复旦")
//		assertEquals "CVCV",ds.getFormal("CVCV")
	}
	
	@Test
	void ala() {
		//println dd.listAliasRelations('复旦大学')
		//println dd.listDualRelationsByKey('复旦大学')
		
		//println dd.listDualRelationsByVal('复旦大学')
		//println ds.getWord('高校')
		ds.t2('高校')
		
	}
	
}

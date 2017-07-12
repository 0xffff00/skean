package party.threebody.s4g.dict;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import party.threebody.s4g.conf.spring.RootConfig;
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("memdb")
public class DictServiceTest {
	@Autowired
	DictService dictService;
	@Test
	public void testListNouns() {
		//TODO unittest
	}

	@Test
	public void testGetNounString() {
		//TODO unittest
	}

	@Test
	public void testGetNounStringString() {
		//TODO unittest
	}

	@Test
	public void testListFirstNodeOfSubTreeByPreOrder() {
		//TODO unittest
	}

	@Test
	public void testListInstances() {
		//TODO unittest
	}

	@Test
	public void listRelatedAlias() {
		System.out.println(dictService.listRelatedAliasObject("复旦大学"));
		System.out.println(dictService.listRelatedAliasObject("复旦大学"));
	}

	@Test
	public void testGetRelated() {
		System.out.println(dictService.getRelatedNested("复旦大学"));
	}

}

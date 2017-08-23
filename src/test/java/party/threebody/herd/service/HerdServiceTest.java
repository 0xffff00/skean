package party.threebody.herd.service;

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
public class HerdServiceTest {

	@Autowired
	HerdService herdService;

	@Test
	public void testSyncCatalogs() {
		herdService.syncCatalogs();
	}


}
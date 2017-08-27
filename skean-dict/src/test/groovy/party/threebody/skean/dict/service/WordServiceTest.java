package party.threebody.skean.dict.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import party.threebody.skean.dict.conf.TestAppConfig;
import party.threebody.skean.dict.dao.WordDao;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("memdb")
public class WordServiceTest {

    @Test
    public void nothing() {
        assert 1 > 0;

    }
}

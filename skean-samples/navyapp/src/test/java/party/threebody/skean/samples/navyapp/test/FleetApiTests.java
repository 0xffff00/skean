package party.threebody.skean.samples.navyapp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.samples.navyapp.domain.Fleet;
import party.threebody.skean.samples.navyapp.domain.Ship;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FleetApiTests {


    @Autowired TestRestTemplate restTemplate;

    @Test
    public void testCRUD() throws Exception {
        // delete all
        restTemplate.delete(new URI("/fleets"));
        // create
        Fleet f1 = new Fleet("CN", "S1", "South 1", "Ha", null);
        Fleet f2 = new Fleet("CN", "S2", "South 2", "mO", null);
        restTemplate.exchange(RequestEntity.post(new URI("/fleets"))
                .accept(APPLICATION_JSON).body(f1), Ship.class);
        restTemplate.exchange(RequestEntity.post(new URI("/fleets"))
                .accept(APPLICATION_JSON).body(f2), Ship.class);
        // verify create
        Object l=restTemplate.getForObject("/fleets",Object.class);
        List<Map> fs1got = restTemplate.getForObject("/fleets/countryCode=CN;code=S1", List.class);
        List<Map> fs2got = restTemplate.getForObject("/fleets/countryCode=CN", List.class);
        assertEquals(2,fs2got.size());
        assertNotNull(fs1got.get(0).get("updateTime"));
        assertEquals(f1.getName(), fs1got.get(0).get("name"));


        // cannot test update ,see : https://jira.spring.io/browse/SPR-15052
       /* // update
        restTemplate.exchange(RequestEntity.patch(new URI("/fleets/countryCode=CN;code=S1"))
                .accept(APPLICATION_JSON).body(Maps.of("leaderName", "Haha")), Map.class);
         verify update
        f1got = restTemplate.getForObject("/fleets/countryCode=CN;code=S1", Ship.class);
        assertNotNull(f1got.getUpdateTime());
        assertEquals("Haha", f1got.getName());
        // delete
        restTemplate.delete(new URI("/fleets/countryCode=CN;code=S1"));
        f1got = restTemplate.getForObject("/fleets/countryCode=CN;code=S1", Ship.class);
        assertNull(f1got.getUpdateTime());*/
    }
}

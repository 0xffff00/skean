package party.threebody.skean.web.eg.navyapp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.web.eg.navyapp.controller.ShipController2;
import party.threebody.skean.web.eg.navyapp.domain.Ship;
import party.threebody.skean.web.eg.navyapp.service.ShipService;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ShipRestApiRealTests {

    @Autowired ShipService shipService;
    @Autowired ShipController2 shipController2;

    @Autowired TestRestTemplate restTemplate;

    @Test
    public void testMultithreadAndTransation() throws Exception {
        //create
        Ship ss01 = new Ship("SS01", "Xuzhou", 12200, 2001);
        Ship ss02 = new Ship("SS02", "Lincang", 12250, 2002);
        Ship ss03 = new Ship("SS03", "Shangqiu", 12187, 2003);

        restTemplate.exchange(RequestEntity.post(new URI("/ships2"))
                .accept(APPLICATION_JSON).body(ss01), Ship.class);
        restTemplate.exchange(RequestEntity.post(new URI("/ships2"))
                .accept(APPLICATION_JSON).body(ss01), Ship.class);
        restTemplate.exchange(RequestEntity.post(new URI("/ships2"))
                .accept(APPLICATION_JSON).body(ss01), Ship.class);

        Ship ss00got = restTemplate.getForObject("/ships2/SS00", Ship.class);
        Ship ss01got = restTemplate.getForObject("/ships2/SS01", Ship.class);
        Ship ss02got = restTemplate.getForObject("/ships2/SS02", Ship.class);
        assertNull(ss00got);
        assertEquals(ss01.toString(), ss01got.toString());
//        ExecutorService exs=Executors.newFixedThreadPool(1000);
//        exs.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    mvc.perform(get("/ships/SS01")).
//                    mvc.perform(patch("/ships/SS01")
//                            .contentType(APPLICATION_JSON)
//                            .content("{\"code\":\"SSN01\",\"birthYear\":2004}"))
//                            .add
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

}
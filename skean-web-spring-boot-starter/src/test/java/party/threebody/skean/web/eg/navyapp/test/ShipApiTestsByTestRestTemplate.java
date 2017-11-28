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
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * not mock but real in-memory database used.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ShipApiTestsByTestRestTemplate {

    @Autowired ShipService shipService;
    @Autowired ShipController2 shipController2;

    @Autowired TestRestTemplate restTemplate;

    @Test
    public void testMultithreadAndTransation() throws Exception {
        //create
        Ship ss01 = new Ship("SS01", "Xuzhou", 12200, 2001);
        Ship ss02 = new Ship("SS02", "Lincang", 10000, 2002);
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
        ExecutorService exs = Executors.newFixedThreadPool(300);
        for (int j = 100; j < 200; j++) {
            exs.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("####### task ######## ");
                    try {
                        Ship ss = restTemplate.getForObject("/ships2/SS01", Ship.class);
                        int w = ss.getWeight();
                        ss.setWeight(w++);
                        restTemplate.exchange(RequestEntity.patch(new URI("/ships2/SS01"))
                                .accept(APPLICATION_JSON).body(ss), Ship.class);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        ss01got = restTemplate.getForObject("/ships2/SS01", Ship.class);
        System.out.println(ss01got.getWeight());

    }

}
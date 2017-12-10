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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * not mock but real in-memory database used.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ShipApiTests {

    @Autowired ShipService shipService;
    @Autowired ShipController2 shipController2;

    @Autowired TestRestTemplate restTemplate;

    @Test
    public void testCSPAndOptAsTailQuery() throws Exception {
        // ----- create -----
        Ship ssn41 = new Ship("SSN41", "SSN41", 20900, 2001);
        Ship ssn42 = new Ship("SSN42", "SSN42", 21030, 2008);
        Ship ssn43 = new Ship("SSN43", "SSN43", 21188, 2010);
        Ship ssn51 = new Ship("SSN51", "SSN51", 22400, 2011);
        Ship ssn52 = new Ship("SSN52", null, 24300, 2011);
        Ship ssn53 = new Ship("SSN53", null, 24321, 2014);
        List<Ship> ssnList = Arrays.asList(ssn41, ssn42, ssn43, ssn51, ssn52, ssn53);

        for (Ship ssn : ssnList) {
            shipService.createAndGet(ssn);
        }
        // ----- read : test OptAsTail-----
        List<Map> list1;
        list1 = restTemplate.getForObject("/ships2/?birthYear=2011", List.class);
        assertEquals(2, list1.size());
        list1 = restTemplate.getForObject("/ships2/?birthYear=2014", List.class);
        assertEquals(1, list1.size());
        list1 = restTemplate.getForObject("/ships2/?code_K=N4", List.class);
        assertEquals(3, list1.size());
        list1 = restTemplate.getForObject("/ships2/?weight_GE=21030&code_KL=SSN4&weight_LE=21188", List.class);
        assertEquals(2, list1.size());
        list1 = restTemplate.getForObject("/ships2/?code_KR=1&weight_GT=20900", List.class);
        assertEquals(1, list1.size());
        list1 = restTemplate.getForObject("/ships2/?code_NKR=3&name_NKL=DD", List.class);
        // [NOT] IN
        assertEquals(3, list1.size());
        list1 = restTemplate.getForObject("/ships2/?weight_LT=23000&birthYear_NIN=2001", List.class);
        assertEquals(3, list1.size());
        list1 = restTemplate.getForObject("/ships2/?birthYear_IN=2010,2011,2014", List.class);
        assertEquals(4, list1.size());
        list1 = restTemplate.getForObject("/ships2/?birthYear_IN=2010&birthYear_IN=2011", List.class);
        assertEquals(3, list1.size());
        list1 = restTemplate.getForObject("/ships2/?code_IN=", List.class);
        assertEquals(0, list1.size());
        list1 = restTemplate.getForObject("/ships2/?code_NIN=", List.class);
        assertEquals(6, list1.size());
        // IS [NOT] NULL
        list1 = restTemplate.getForObject("/ships2/?name_IS=NULL", List.class);
        assertEquals(2, list1.size());
        list1 = restTemplate.getForObject("/ships2/?name_ISNOT=NULL&birthYear_GE=2011", List.class);
        assertEquals(1, list1.size());
        // ----- delete all -----
        shipService.deleteAll();


    }

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
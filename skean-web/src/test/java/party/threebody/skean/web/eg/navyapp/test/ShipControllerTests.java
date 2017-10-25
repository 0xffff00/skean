package party.threebody.skean.web.eg.navyapp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.RequestContextUtils;
import party.threebody.skean.web.eg.navyapp.domain.Ship;
import party.threebody.skean.web.eg.navyapp.service.ShipService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShipControllerTests {

    @Autowired
    ShipService shipService;
    @Autowired
    MockMvc mvc;

    @Test
    public void testCrud() throws Exception {
        //create
        mvc.perform(post("/ships")
                .contentType(APPLICATION_JSON)
                .content("{\"code\":\"SS01\",\"birthYear\":2001,\"name\":\"Zhenzhou\"}")
        ).andExpect(status().is2xxSuccessful());

        mvc.perform(get("/ships/"))
                .andExpect(status().isOk())
                .andExpect(
                        content().json("[{\"code\":\"SS01\",\"birthYear\":2001,\"name\":\"Zhenzhou\"}]")
                );

        mvc.perform(put("/ships/SS01")
                .contentType(APPLICATION_JSON)
                .content("{\"code\":\"SS01\",\"birthYear\":2003,\"name\":\"Zhenzhou\"}")
        ).andExpect(status().is2xxSuccessful());

        assertEquals(Integer.valueOf(2003), shipService.getShip("SS01").getBirthYear());

        mvc.perform(patch("/ships/SS01")
                .contentType(APPLICATION_JSON)
                .content("{\"code\":\"SSN01\",\"birthYear\":2004}")
        ).andExpect(status().is2xxSuccessful());

        assertEquals(Integer.valueOf(2004), shipService.getShip("SSN01").getBirthYear());
        assertEquals("Zhenzhou", shipService.getShip("SSN01").getName());
        assertEquals(1, shipService.countShips(null));

        mvc.perform(delete("/ships/SSN01")
        ).andExpect(status().is2xxSuccessful());
        mvc.perform(delete("/ships/SSN01")
        ).andExpect(status().isGone());

        assertEquals(0, shipService.countShips(null));

    }

    @Test
    public void testMultithreadAndTransation() throws Exception{
        //create
        RestTemplate restTemplate=new RestTemplate();
        Ship ss01=new Ship("SS01","Zhenzhou",12200,2001);
        ;
        restTemplate.postForObject("/ships",new HttpEntity<Ship>(ss01),Ship.class);

        Ship ss01got=restTemplate.getForObject("/ships/SS01",Ship.class);
        assertEquals(ss01.toString(),ss01got.toString());
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
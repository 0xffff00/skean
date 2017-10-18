package party.threebody.skean.web.eg.navyapp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import party.threebody.skean.web.eg.navyapp.service.ShipService;

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

}
package party.threebody.skean.web.eg.navyapp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import party.threebody.skean.web.eg.navyapp.controller.ShipController;
import party.threebody.skean.web.eg.navyapp.controller.ShipController2;
import party.threebody.skean.web.eg.navyapp.domain.Ship;
import party.threebody.skean.web.eg.navyapp.service.ShipService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShipRestApiMockTests {

    @Autowired ShipService shipService;
    @Autowired MockMvc mvc;
    @Autowired ShipController2 shipController2;
    @Autowired ShipController shipController;

    @Test
    public void test1controller() throws Exception {
        shipController.httpCreate(new Ship("DD01", "Zhongguancun", 6432, 2021));
        Ship dd01 = shipService.getShip("DD01");
        assertNotNull(dd01);
        shipController.httpDelete("DD01");
        dd01 = shipService.getShip("DD01");
        assertNull(dd01);

    }

    @Test
    public void test2controller() throws Exception {
        shipController2.httpCreate(new Ship("DD01", "Zhongguancun", 6432, 2021));
        Ship dd01 = shipService.getShip("DD01");
        assertEquals(6432, (int) dd01.getWeight());
        shipController2.httpDelete("DD01");
        dd01 = shipService.getShip("DD01");
        assertNull(dd01);

    }

    @Test
    public void test1http() throws Exception {
        testShipsApis("/ships");
    }

    @Test
    public void test2http() throws Exception {
        testShipsApis("/ships2");
    }

    void testShipsApis(String rootPath) throws Exception {
        //create
        mvc.perform(post(rootPath)
                .contentType(APPLICATION_JSON)
                .content("{\"code\":\"SS01\",\"birthYear\":2001,\"name\":\"Zhenzhou\"}")
        ).andExpect(status().is2xxSuccessful());

        mvc.perform(get(rootPath + "/"))
                .andExpect(status().isOk())
                .andExpect(
                        content().json("[{\"code\":\"SS01\",\"birthYear\":2001,\"name\":\"Zhenzhou\"}]")
                );

        mvc.perform(put(rootPath + "/SS01")
                .contentType(APPLICATION_JSON)
                .content("{\"code\":\"SS01\",\"birthYear\":2003,\"name\":\"Zhenzhou\"}")
        ).andExpect(status().is2xxSuccessful());

        assertEquals(Integer.valueOf(2003), shipService.getShip("SS01").getBirthYear());

        mvc.perform(patch(rootPath + "/SS01")
                .contentType(APPLICATION_JSON)
                .content("{\"code\":\"SSN01\",\"birthYear\":2004}")
        ).andExpect(status().is2xxSuccessful());

        assertEquals(Integer.valueOf(2004), shipService.getShip("SSN01").getBirthYear());
        assertEquals("Zhenzhou", shipService.getShip("SSN01").getName());
        assertEquals(1, shipService.countShips(null));

        mvc.perform(delete(rootPath + "/SSN01")
        ).andExpect(status().is2xxSuccessful());
        mvc.perform(delete(rootPath + "/SSN01")
        ).andExpect(status().isGone());

        assertEquals(0, shipService.countShips(null));

    }

}
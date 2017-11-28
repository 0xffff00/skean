package party.threebody.skean.dict.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.dict.domain.Word;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DictApiTests {

    @Autowired TestRestTemplate restTemplate;

    @Ignore
    @Test
    public void t1() throws Exception{
        Word w1=new Word();
        w1.setText("Apple");
        restTemplate.exchange(RequestEntity.post(new URI("/words"))
                .accept(APPLICATION_JSON).body(w1), Word.class);

        Word w1got = restTemplate.getForObject("/words/Apple", Word.class);



        assertEquals("Apple",w1got.getText());
        assertNotNull(w1got.getUpdateTime());

    }
}

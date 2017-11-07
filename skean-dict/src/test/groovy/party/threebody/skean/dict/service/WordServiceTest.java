package party.threebody.skean.dict.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordServiceTest {

    @Autowired
    WordService wordService;
    @Test
    public void nothing() {
        assert 1 > 0;

    }

}

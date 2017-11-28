package party.threebody.skean.dict.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.dict.domain.Word;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordServiceTest {

    @Autowired
    WordService wordService;
    @Test
    public void t1() {

        assertEquals(0,wordService.countWords(null));
        Word w1=new Word();
        w1.setText("Apple");
        wordService.createWord(w1);

        assertEquals(1,wordService.countWords(null));
        Word w1got=wordService.getWord("Apple");
        assertNotNull(w1got.getUpdateTime());
//        w1got=wordService.getWordWithRels("Apple");
//
//        assertNotNull(w1got.getUpdateTime());
    }

}

package party.threebody.skean.dict.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.domain.Word;
import party.threebody.skean.dict.service.WordService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordServiceTest {
    @Autowired
    WordDao wordDao;
    @Autowired
    WordService wordService;

    @Test
    public void testCrud() {
        int wordCnt = wordService.countWords(null);   // current is 34
        Word w1 = new Word();
        w1.setText("Apple");
        // create
        wordService.createWord(w1);

        assertEquals(wordCnt + 1, wordService.countWords(null));
        assertEquals(1, wordService.countWords(Criteria.of(new BasicCriterion("text", "Apple"))));
        // read
        Word w1got = wordService.getWord("Apple");
        assertNotNull(w1got.getUpdateTime());
        // update
        w1got.setDesc("haha");
        wordDao.updateByExample(w1got);
        assertEquals("haha", wordDao.readOne("Apple").getDesc());
        //delete
        wordDao.deleteByExample(w1got);
        assertEquals(wordCnt, wordService.countWords(null));
    }

}

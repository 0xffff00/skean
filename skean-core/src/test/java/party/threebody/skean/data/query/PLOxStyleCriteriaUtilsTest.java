package party.threebody.skean.data.query;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PLOxStyleCriteriaUtilsTest {
    @Test
    public void isLegalParamName() throws Exception {
        assertTrue(PLOxStyleCriteriaUtils.isLegalParamName("_3"));
        assertTrue(PLOxStyleCriteriaUtils.isLegalParamName("_.3"));
        assertTrue(PLOxStyleCriteriaUtils.isLegalParamName("aa_3"));
        assertTrue(PLOxStyleCriteriaUtils.isLegalParamName("a"));
        assertTrue(PLOxStyleCriteriaUtils.isLegalParamName("__$$1._"));
        assertFalse(PLOxStyleCriteriaUtils.isLegalParamName("3"));
        assertFalse(PLOxStyleCriteriaUtils.isLegalParamName("a#"));
        assertFalse(PLOxStyleCriteriaUtils.isLegalParamName("f3 "));
        assertFalse(PLOxStyleCriteriaUtils.isLegalParamName(""));
    }


}
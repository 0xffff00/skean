package party.threebody.skean.mvc.util;

import org.junit.Test;
import party.threebody.skean.core.query.BasicCriterion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CriterionBuildUtilsTest {
    @Test
    public void test() {
        String s1 = "[{\"n\":\"name1\",\"o\":\"=\",\"v\":\"Tom\"},{\"n\":\"age\",\"o\":\">\",\"v\":20}]";
        BasicCriterion[] crits = CriterionBuildUtils.buildBasicCriterionArray(s1);
        assertEquals("name1", crits[0].getName());
        assertEquals(20, crits[1].getValue());
    }
    @Test
    public void isLegalParamName(){
        assertTrue(CriterionBuildUtils.isLegalParamName("_3"));
        assertTrue(CriterionBuildUtils.isLegalParamName("_.3"));
        assertTrue(CriterionBuildUtils.isLegalParamName("aa_3"));
        assertTrue(CriterionBuildUtils.isLegalParamName("a"));
        assertTrue(CriterionBuildUtils.isLegalParamName("__$$1._"));
        assertFalse(CriterionBuildUtils.isLegalParamName("3"));
        assertFalse(CriterionBuildUtils.isLegalParamName("a#"));
        assertFalse(CriterionBuildUtils.isLegalParamName("f3 "));
        assertFalse(CriterionBuildUtils.isLegalParamName(""));

    }

}

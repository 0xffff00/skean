package party.threebody.skean.mvc.util;

import org.junit.Test;
import party.threebody.skean.core.query.BasicCriterion;

import static org.junit.Assert.assertEquals;

public class CriterionBuildUtilsTest {
    @Test
    public void test() {
        String s1 = "[{\"n\":\"name1\",\"o\":\"=\",\"v\":\"Tom\"},{\"n\":\"age\",\"o\":\">\",\"v\":20}]";
        BasicCriterion[] crits = CriterionBuildUtils.buildBasicCriterionArray(s1);
        assertEquals("name1", crits[0].getName());
        assertEquals(20, crits[1].getValue());
    }

}

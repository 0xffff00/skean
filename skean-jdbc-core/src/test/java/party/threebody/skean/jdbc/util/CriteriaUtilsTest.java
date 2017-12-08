package party.threebody.skean.jdbc.util;

import org.junit.Test;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.jdbc.util.CriteriaUtils;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CriteriaUtilsTest {


    public void testToClausesAndArgs() {

    }

    private static void assert1(String expectedClause, Object expectedFirstArg, BasicCriterion c) {
        ClauseAndArgs caa = CriteriaUtils.toClauseAndArgs(c);
        Object actualFirstArg = (caa.getArgs() != null && caa.getArgs().length > 0) ? caa.getArgs()[0] : null;
        assertEquals(expectedClause, caa.getClause());
        assertEquals(expectedFirstArg, actualFirstArg);
    }

    @Test
    public void testToClauseAndArgs() {
        assert1("Age=?", 12,
                new BasicCriterion("Age", 12));
        assert1("Name=?", "M2@",
                new BasicCriterion("Name", "M2@"));
        assert1("Name LIKE ?", "%M2@%",
                new BasicCriterion("Name", "like", "M2@"));
        assert1("Name LIKE ?", "M2@%",
                new BasicCriterion("Name", "likeLeft", "M2@"));
        assert1("Name LIKE ?", "%M2@",
                new BasicCriterion("Name", "likeRight", "M2@"));
        assert1("key IN ?", 12,
                new BasicCriterion("key", "in", 12));
        assert1("key NOT IN (?,?)", 12,
                new BasicCriterion("key", "notIn", Arrays.asList(12, 32)));
        assert1("key IN (?,?,?)", 12,
                new BasicCriterion("key", "in", Arrays.asList(12, "", "6")));

    }


    @Test
    public void buildClauseOfInStrs() {
        assertTrue(CriteriaUtils.buildClauseOfInStrs("col1", null).endsWith("NOTHING'"));
        assertTrue(CriteriaUtils.buildClauseOfInStrs("col1", Arrays.asList()).endsWith("NOTHING'"));
        assertEquals("col1 IN ('')",
                CriteriaUtils.buildClauseOfInStrs("col1", Arrays.asList("")));
        assertEquals("col1 IN ('1')",
                CriteriaUtils.buildClauseOfInStrs("col1", Arrays.asList("1")));
        assertEquals("col1 IN ('1','','')",
                CriteriaUtils.buildClauseOfInStrs("col1", Arrays.asList("1","","")));
    }

    @Test
    public void buildClauseOfInNums() {
        assertTrue(CriteriaUtils.buildClauseOfInNums("col1", null).endsWith("NOTHING'"));
        assertTrue(CriteriaUtils.buildClauseOfInNums("col1", Arrays.asList()).endsWith("NOTHING'"));
        assertEquals("col1 IN (0)",
                CriteriaUtils.buildClauseOfInNums("col1", Arrays.asList(0)));
        assertEquals("col1 IN (0,0,1)",
                CriteriaUtils.buildClauseOfInNums("col1", Arrays.asList(0,0,1)));
    }


}

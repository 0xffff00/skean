package party.threebody.skean.jdbc.util;

import org.junit.Test;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.jdbc.util.CriteriaUtils;

import java.util.Arrays;

public class CriteriaUtilsTest {


	public void testToClausesAndArgs() {

	}


	public void testToClauseAndArgs() {
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Age", 12)));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Name", "M2@")));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Name", "~", "M2@")));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Name", "^", "M2@")));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Name", "$", "M2@")));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Age", "not in", "12")));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Age", "not in", Arrays.asList(12, 32))));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Age", "in", Arrays.asList(12, "22x"))));
		System.out.println(CriteriaUtils.toClauseAndArgs(new BasicCriterion("Age", "in", Arrays.asList(12, 32, 75))));
	}

}

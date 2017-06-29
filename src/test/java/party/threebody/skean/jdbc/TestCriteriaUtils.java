package party.threebody.skean.jdbc;

import java.util.Arrays;

import org.junit.Test;

import party.threebody.skean.core.query.BasicCriterion;
import party.threebody.skean.jdbc.util.CriteriaUtils;

public class TestCriteriaUtils {

	
	@Test
	public void testToClausesAndArgs() {

	}

	@Test
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

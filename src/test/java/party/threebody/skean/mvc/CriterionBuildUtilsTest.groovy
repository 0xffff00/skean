package party.threebody.skean.mvc;

import static org.junit.Assert.*;

import org.junit.Test;

import party.threebody.skean.mvc.util.CriterionBuildUtils

public class CriterionBuildUtilsTest {

	@Test
	public void test() {
		def s1='''[{"n":"name1","o":"=","v":"Tom"},{"n":"age","o":">","v":20}]'''
		def crits=CriterionBuildUtils.buildBasicCriterionArray(s1);
		assert crits[0].name=="name1"
		assert crits[1].value==20
		assert crits[1].v==20
	}

}

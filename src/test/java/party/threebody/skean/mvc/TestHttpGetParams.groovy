package party.threebody.skean.mvc;

import static org.junit.Assert.*;

import org.junit.Test;

import party.threebody.skean.core.query.QueryParamsSuite


public class TestParamsBuildUtils {

	@Test
	public void checkNameLegality() {
		
		
		QueryParamsSuite p1=new QueryParamsSuite(null,null,null,null);
		p1=new QueryParamsSuite('age','''[["id","in",[35,56,57]],["state","A"]]''',1,10);
		
	}
	

}

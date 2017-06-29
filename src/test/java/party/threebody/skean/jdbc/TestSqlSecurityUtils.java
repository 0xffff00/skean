package party.threebody.skean.jdbc;

import static org.junit.Assert.*;

import org.junit.Test;

import party.threebody.skean.jdbc.util.SqlSecurityUtils;

public class TestSqlSecurityUtils {

	@Test
	public void checkNameLegality() {
		assert SqlSecurityUtils.checkNameLegality("_24");
		assert SqlSecurityUtils.checkNameLegality("A_3a");
		assert !SqlSecurityUtils.checkNameLegality("A,");
		assert !SqlSecurityUtils.checkNameLegality("\'A\'");
		assert SqlSecurityUtils.checkNameLegality("`Aaa`");
		assert !SqlSecurityUtils.checkNameLegality("`Aaa`s");
		assert !SqlSecurityUtils.checkNameLegality("`Aaa``");
		assert !SqlSecurityUtils.checkNameLegality("Aaa ");
	}
	
	@Test
	public void checkColsOrderByLegality() {
		assert SqlSecurityUtils.checkColsOrderByLegality("x1 asc");
		assert SqlSecurityUtils.checkColsOrderByLegality("x1 desc");
		assert SqlSecurityUtils.checkColsOrderByLegality("x1");
		assert SqlSecurityUtils.checkColsOrderByLegality("`x1`");
		assert SqlSecurityUtils.checkColsOrderByLegality("`x1` asc");
		assert SqlSecurityUtils.checkColsOrderByLegality("`x1` desc");
		assert SqlSecurityUtils.checkColsOrderByLegality("x1 ASC");
		assert SqlSecurityUtils.checkColsOrderByLegality("`x1` DESC");
	}

}

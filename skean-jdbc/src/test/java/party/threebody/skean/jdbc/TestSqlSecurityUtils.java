package party.threebody.skean.jdbc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import party.threebody.skean.jdbc.util.SqlSecurityUtils;

public class TestSqlSecurityUtils {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkColumnNameLegality() {
        SqlSecurityUtils.checkColumnNameLegality("_24");
        SqlSecurityUtils.checkColumnNameLegality("A_3a");

        thrown.expect(SkeanSqlSecurityException.class);
        SqlSecurityUtils.checkColumnNameLegality("A,");

        thrown.expect(SkeanSqlSecurityException.class);
        SqlSecurityUtils.checkColumnNameLegality("\'A\'");

        thrown.expect(SkeanSqlSecurityException.class);
        SqlSecurityUtils.checkColumnNameLegality("`Aaa`s");

        thrown.expect(SkeanSqlSecurityException.class);
        SqlSecurityUtils.checkColumnNameLegality("`Aaa``");

        thrown.expect(SkeanSqlSecurityException.class);
        SqlSecurityUtils.checkColumnNameLegality("Aaa");

    }


}

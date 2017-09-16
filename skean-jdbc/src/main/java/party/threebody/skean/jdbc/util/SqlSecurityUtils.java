package party.threebody.skean.jdbc.util;


import party.threebody.skean.core.SkeanSqlSecurityException;

public class SqlSecurityUtils {
    /**
     * check table name legality.<br>
     * Helping prevent persistence layer from SQL injection attack.
     *
     * @param name
     * @throws SkeanSqlSecurityException
     */
    public static void checkTableNameLegality(String name) {
        if (name == null) {
            throw new SkeanSqlSecurityException("illegal table name: null");
        }
        if (!name.matches("\\w+")) {
            throw new SkeanSqlSecurityException("illegal table name: \"" + name + "\"");
        }
    }

    /**
     * check column name or table name legality.<br>
     * Helping prevent persistence layer from SQL injection attack.
     *
     * @param name
     * @throws SkeanSqlSecurityException
     */
    public static void checkColumnNameLegality(String name) {
        // TODO let simple function wrapper pass. eg. upper(col1)
        if (name == null) {
            throw new SkeanSqlSecurityException("illegal column name: null");
        }
        if (!name.matches("\\w+")) {
            throw new SkeanSqlSecurityException("illegal column name: \"" + name + "\"");
        }
    }


}
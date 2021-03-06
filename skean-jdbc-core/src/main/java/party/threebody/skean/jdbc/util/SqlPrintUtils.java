/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.jdbc.util;

import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;

public final class SqlPrintUtils {
    public ChainedJdbcTemplateContext context;

    public static final String ANSI_COLOR_KEYWORD = ANSI.PURPLE;
    public static final String ANSI_COLOR_ARG = ANSI.CYAN;

    private SqlPrintUtils() {
    }

    /**
     * format sql & args to ANSI String, args will be appended to sql.
     *
     * @param sql
     * @param args
     * @return
     */
    public static String ansiFormatSqlWithPlaceholders(String sql, Object[] args) {
        if (sql == null) {
            return null;
        }
        String res = sql;

        for (String kw : ANSI.KEYWORDS_SQL2003) {
            res = res.replaceAll("\\b" + kw + "\\b", ANSI_COLOR_KEYWORD + kw + ANSI.RESET);
            res = res.replaceAll("\\b" + kw.toLowerCase() + "\\b", ANSI_COLOR_KEYWORD + kw + ANSI.RESET);
        }

        if (args != null && args.length > 0) {
            StringBuilder res1 = new StringBuilder(res);
            res1.append("\n").append(ANSI.PURPLE);
            for (int i = 0, n = args.length; i < n; i++) {
                res1.append(args[i]);
                if (i != n - 1) {
                    res1.append(",");
                }
            }
            res1.append(ANSI.RESET);
            res = res1.toString();
        }

        return res;

    }

    /**
     * format sql & args to ANSI String, each '?' placeholder is replaced to an arg.
     *
     * @param sql
     * @param args
     * @return the returned sql ,in which each '?' placeholder is replaced to an arg.
     */
    public static String ansiFormatSql(String sql, Object[] args) {
        if (sql == null) {
            return null;
        }
        String res = sql;

        for (String kw : ANSI.KEYWORDS_SQL2003) {
            res = res.replaceAll("\\b" + kw + "\\b", ANSI_COLOR_KEYWORD + kw + ANSI.RESET);
            res = res.replaceAll("\\b" + kw.toLowerCase() + "\\b", ANSI_COLOR_KEYWORD + kw + ANSI.RESET);
        }

        if (args != null && args.length > 0) {
            StringBuilder res1 = new StringBuilder(res);

            for (int i = 0, k = -1, n = args.length; i < n; i++) {
                k = res1.indexOf("?", k - 1);
                res1.replace(k, k + 1, ansiFormatArg(args[i]));
            }
            res1.append(ANSI.RESET);
            res = res1.toString();
        }

        return res;

    }

    private static String ansiFormatArg(Object arg) {
        if (arg == null) {
            return ANSI_COLOR_ARG + "null" + ANSI.RESET;
        }
        if (arg instanceof Number) {
            return ANSI_COLOR_ARG + arg.toString() + ANSI.RESET;
        }
        if (arg instanceof String || arg instanceof Character) {
            return ANSI_COLOR_ARG + "'" + arg + "'" + ANSI.RESET;
        }
        return ANSI_COLOR_ARG + arg.toString() + ANSI.RESET;
    }

    public static String ansiFormat(String text, String ansiColor) {
        if (text == null) {
            return null;
        }
        return ansiColor + text + ANSI.RESET;

    }

    public static class ANSI {
        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";
        public static final String BLACK_BG = "\u001B[40m";
        public static final String RED_BG = "\u001B[41m";
        public static final String GREEN_BG = "\u001B[42m";
        public static final String YELLOW_BG = "\u001B[43m";
        public static final String BLUE_BG = "\u001B[44m";
        public static final String PURPLE_BG = "\u001B[45m";
        public static final String CYAN_BG = "\u001B[46m";
        public static final String WHITE_BG = "\u001B[47m";
        public static final String[] KEYWORDS_SQL2003 = {"ADD", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE",
                "ARRAY", "AS", "ASENSITIVE", "ASYMMETRIC", "AT", "ATOMIC", "AUTHORIZATION", "BEGIN", "BETWEEN",
                "BIGINT", "BINARY", "BLOB", "BOOLEAN", "BOTH", "BY", "CALL", "CALLED", "CASCADED", "CASE", "CAST",
                "CHAR", "CHARACTER", "CHECK", "CLOB", "CLOSE", "COLLATE", "COLUMN", "COMMIT", "CONDITION", "CONNECT",
                "CONSTRAINT", "CONTINUE", "CORRESPONDING", "CREATE", "CROSS", "CUBE", "CURRENT", "CURRENT_DATE",
                "CURRENT_DEFAULT_TRANSFORM_GROUP", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP",
                "CURRENT_TRANSFORM_GROUP_FOR_TYPE", "CURRENT_USER", "CURSOR", "CYCLE", "DATE", "DAY", "DEALLOCATE",
                "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELETE", "DEREF", "DESCRIBE", "DETERMINISTIC", "DISCONNECT",
                "DISTINCT", "DO", "DOUBLE", "DROP", "DYNAMIC", "EACH", "ELEMENT", "ELSE", "ELSEIF", "END", "ESCAPE",
                "EXCEPT", "EXEC", "EXECUTE", "EXISTS", "EXIT", "EXTERNAL", "FALSE", "FETCH", "FILTER", "FLOAT", "FOR",
                "FOREIGN", "FREE", "FROM", "FULL", "FUNCTION", "GET", "GLOBAL", "GRANT", "GROUP", "GROUPING", "HANDLER",
                "HAVING", "HOLD", "HOUR", "IDENTITY", "IF", "IMMEDIATE", "IN", "INDICATOR", "INNER", "INOUT", "INPUT",
                "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN",
                "LANGUAGE", "LARGE", "LATERAL", "LEADING", "LEAVE", "LEFT", "LIKE", "LOCAL", "LOCALTIME",
                "LOCALTIMESTAMP", "LOOP", "MATCH", "MEMBER", "MERGE", "METHOD", "MINUTE", "MODIFIES", "MODULE", "MONTH",
                "MULTISET", "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NEW", "NO", "NONE", "NOT", "NULL", "NUMERIC",
                "OF", "OLD", "ON", "ONLY", "OPEN", "OR", "ORDER", "OUT", "OUTER", "OUTPUT", "OVER", "OVERLAPS",
                "PARAMETER", "PARTITION", "PRECISION", "PREPARE", "PRIMARY", "PROCEDURE", "RANGE", "READS", "REAL",
                "RECURSIVE", "REF", "REFERENCES", "REFERENCING", "RELEASE", "REPEAT", "RESIGNAL", "RESULT", "RETURN",
                "RETURNS", "REVOKE", "RIGHT", "ROLLBACK", "ROLLUP", "ROW", "ROWS", "SAVEPOINT", "SCOPE", "SCROLL",
                "SEARCH", "SECOND", "SELECT", "SENSITIVE", "SESSION_USER", "SET", "SIGNAL", "SIMILAR", "SMALLINT",
                "SOME", "SPECIFIC", "SPECIFICTYPE", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "START", "STATIC",
                "SUBMULTISET", "SYMMETRIC", "SYSTEM", "SYSTEM_USER", "TABLE", "TABLESAMPLE", "THEN", "TIME",
                "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSLATION", "TREAT", "TRIGGER",
                "TRUE", "UNDO", "UNION", "UNIQUE", "UNKNOWN", "UNNEST", "UNTIL", "UPDATE", "USER", "USING", "VALUE",
                "VALUES", "VARCHAR", "VARYING", "WHEN", "WHENEVER", "WHERE", "WHILE", "WINDOW", "WITH", "WITHIN",
                "WITHOUT", "YEAR", "LIMIT", "OFFSET", "ASC", "DESC","COUNT"};

    }
}
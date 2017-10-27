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

package party.threebody.skean.jdbc.phrase;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import party.threebody.skean.collections.Maps;
import party.threebody.skean.data.query.Criterion;
import party.threebody.skean.data.query.SortingField;
import party.threebody.skean.jdbc.ChainedJdbcTemplateException;
import party.threebody.skean.jdbc.util.*;
import party.threebody.skean.lang.StringCases;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlBuilderMysqlImpl implements SqlBuilder {

    static Logger logger = LoggerFactory.getLogger(SqlBuilderMysqlImpl.class);
    private SqlBuilderConfig conf;

    final static char BL = ' '; // Blank
    final static char BQ = '`'; // Back Quote
    final static char NL = '\n'; // New Line
    final static char CM = ','; // Comma
    final static char SC = ';'; // Semicolon

    private String dlmt; // delimiter
    private String nq; // name quote (such as back quote or empty)

    @Override
    public void setConfig(SqlBuilderConfig conf) {
        this.conf = conf;
        dlmt = conf.getDlmt();
        nq = conf.getNameQuote();
    }

    public SqlBuilderMysqlImpl(SqlBuilderConfig conf) {
        setConfig(conf);
    }


    @Override
    public SqlAndArgs buildSelectSql(FromPhrase p) {
        mayConvertAllParamNamesToSnakeCase(p);
        StringBuilder sql = new StringBuilder();
        String sels = "*";
        if (p.afCols != null) {
            throw new ChainedJdbcTemplateException("illegal phrase 'affect' for SELECT SQL building.");
        }
        // PRINT>>>> SELECT... FROM...

        if (p.originSelectSql == null) {
            // Handle 'from()','select()'
            if (p.seCols != null) {
                sels = joinNamesByComma(p.seCols, nq);
            } else if (p.enableCount) {
                sels = "count(*) cnt"; // simple counting col
            }

            sql.append("SELECT ").append(sels).append(BL);
            if (sels.length() > conf.getMaxCharsOfInlineSelCols()) {
                sql.append(dlmt);
            }
            sql.append("FROM ").append(nq).append(p.table).append(nq).append(BL);
            if (p.enableCount) {
                if (p.tableAlias == null && p.seCols == null) {
                    p.tableAlias = "t";
                }
            }
            if (p.tableAlias != null) {
                sql.append(p.tableAlias).append(BL);
            }
        } else {
            // Handle 'fromSql()'
            sql.append(p.originSelectSql).append(BL);
        }


        // PRINT>>>> WHERE...
        ClauseAndArgs wherePart = buildWhereClauseAndArgs(p);
        sql.append(wherePart.getClause());
        // Handle 'count()'
        if (p.enableCount) {
            if (p.seCols != null) {
                // wrap sql with 'count'
                wrapSqlByCount(sql);
            }
        }

        // Handle 'orderBy()'
        if (ArrayUtils.isNotEmpty(p.sortingFields)) {
            sql.append(dlmt).append("ORDER BY ");
            for (int i = 0, n = p.sortingFields.length; i < n; i++) {
                String oc = buildOrderByClause(p.sortingFields[i], nq);
                sql.append(oc);
                if (i != n - 1) {
                    sql.append(CM);
                }
            }
        }

        // Handle 'page()','limit()','offset()'
        if (!p.enableCount && p.limit > 0) {
            sql.append(dlmt).append("LIMIT ");
            if (p.offset > 0) {
                sql.append(p.offset).append(CM);
            }
            sql.append(p.limit);
        }

        // return the result
        return new SqlAndArgs(sql.toString(), wherePart.getArgs());

    }

    /**
     * build 'WHERE' clause after 'SELECT', 'UPDATE' & 'DELETE'
     *
     * @param p
     * @return
     */
    private ClauseAndArgs buildWhereClauseAndArgs(FromPhrase p) {
        StringBuilder sql = new StringBuilder();
        Object[] args = null;
        // (1/3)Handle 'criteria()'
        if (p.criteria != null) {
            ClausesAndArgs cp = CriteriaUtils.toClausesAndArgs(p.criteria);
            if (ArrayUtils.isNotEmpty(cp.getClauses())) {
                for (int i = 0, n = cp.getClauses().length; i < n; i++) {
                    sql.append(dlmt).append("  AND ").append(cp.getClause(i)).append(BL);
                }
            }
            args = cp.getArgs();
        }

        // (2/3)Handle 'by()'
        if (p.byCols != null) {

            Object[] untranslatedArgs = buildUntranslatedArgsOfWhereClause(p);
            args = translateArgs(untranslatedArgs);
            // consider null value situation
            for (int i = 0, n = p.byCols.length; i < n; i++) {
                sql.append(dlmt).append("  AND ");
                if (untranslatedArgs[i] == null) {
                    sql.append(nq).append(p.byCols[i]).append(nq).append(" IS NULL");
                } else if (untranslatedArgs[i] instanceof Collection) {
                    Collection<?> coll = (Collection<?>) untranslatedArgs[i];
                    int len = coll.size();
                    if (len == 0) {    //handle invalid SQL clause: 'IN ()'
                        sql.append("'").append(p.byCols[i]).append(" IN'='NOTHING'");
                    } else {
                        sql.append(nq).append(p.byCols[i]).append(nq)
                                .append(" IN ").append(CriteriaUtils.generateQMarkArrStrWithComma(len));
                    }

                } else {
                    sql.append(nq).append(p.byCols[i]).append(nq).append("=?");
                }
                sql.append(BL);
            }
        }

        // (3/3)Handle 'where()'
        if (p.whereClauses != null) {
            args = buildUntranslatedArgsOfWhereClause(p);
            for (int i = 0, n = p.whereClauses.length; i < n; i++) {
                sql.append(dlmt).append("  AND ").append(p.whereClauses[i]).append(BL);
            }

        }
        if (sql.length() > 5) {
            sql.replace(0, dlmt.length() + 6, dlmt + "WHERE ");
        }
        return new ClauseAndArgs(sql.toString(), args);
    }

    private static Object[] buildNullableArgsOfAffectClause(FromPhrase p) {
        if (p.afVal.enabled()) { // handle: 'affect(...).val(...)'
            if (p.afVal.getArr() != null) {
                if (p.afCols.length != p.afVal.getArr().length) {
                    throw new ChainedJdbcTemplateException(
                            "amount of 'args' & 'cols' in 'affect(cols).val(args)' mismatched.");
                }
                return p.afVal.getArr();
            }
            if (p.afVal.getMap() != null) {
                return ArrayAndMapUtils.getValsArr(p.afVal.getMap(), p.afCols);
            }
            if (p.afVal.getObj() != null) {
                return JavaBeans.getProperties(p.afVal.getObj(), p.afCols);
            }
            throw new ChainedJdbcTemplateException("build args of 'INSERT' or 'UPDATE' clause failed. ");
        }

        if (p.val.enabled()) {
            if (p.val.getArr() != null) { // handle:
                // 'affect(...).[by|where](...).val(...)'
                if (p.val.getArr().length < p.afCols.length) {
                    throw new ChainedJdbcTemplateException(
                            "amount of 'args' should not less than 'cols' in 'affect(cols).[by|where](...).val(args)'.");

                }
                return Arrays.copyOfRange(p.val.getArr(), 0, p.afCols.length);
            }
            if (p.val.getMap() != null) {
                return ArrayAndMapUtils.getValsArr(p.val.getMap(), p.afCols);
            }
            if (p.val.getObj() != null) {
                return JavaBeans.getProperties(p.val.getObj(), p.afCols);
            }
        }
        throw new ChainedJdbcTemplateException("build args of 'INSERT' or 'UPDATE' clause failed. ");
    }

    /**
     * build an untranslated arg array
     */
    private static Object[] buildUntranslatedArgsOfWhereClause(FromPhrase p) {
        if (!p.val.enabled()) {
            return null;
        }
        if (p.val.getArr() != null) {
            if (p.afCols != null) {
                if (p.afVal.enabled()) { // handle: 'affect(...).val(...)'
                    if (p.byCols.length != p.val.getArr().length) {
                        throw new ChainedJdbcTemplateException(
                                "amount of 'args' & 'cols' in 'affect(...).val(...).by(cols).val(args)' mismatched.");
                    }
                    return p.val.getArr();
                } else {

                    if (p.byCols != null) { // handle: 'affect(...).by(...).val(...)'
                        if (p.afCols.length + p.byCols.length != p.val.getArr().length) {
                            throw new ChainedJdbcTemplateException(
                                    "amount of 'args' & 'colsA+colsB' in 'affect(colsA).by(colsB).val(args)' mismatched.");
                        }

                    }
                    if (p.whereClauses != null) { // handle: 'affect(...).where(...).val(...)'
                        // nothing to check
                    }
                    // consider not all byVal is used to 'by()',but also to 'affect()'
                    return Arrays.copyOfRange(p.val.getArr(), p.afCols.length, p.val.getArr().length);
                }
            } else {
                return p.val.getArr();
            }

        }
        if (p.val.getMap() != null) { // handle: 'by(...).val(map)'
            return ArrayAndMapUtils.getValsArr(p.val.getMap(), p.byCols);
        }
        if (p.val.getObj() != null) {
            return JavaBeans.getProperties(p.val.getObj(), p.byCols);
        }
        throw new ChainedJdbcTemplateException("build args of 'WHERE' clause failed. ");
    }


    /**
     * translate args
     * =========================
     * before -> after       use for clause
     * normal arg:     1 -> 1           col = ?
     * null arg :      1 -> 0           col IS NULL/IS NOT NULL
     * collection arg: 1 -> N           col IN (?,?..?)
     */
    private static Object[] translateArgs(Object[] args) {
        return Stream.of(args).flatMap(arg -> {
            if (arg == null) {
                return Stream.empty();
            }
            if (arg instanceof Collection) {
                Collection coll = (Collection) arg;
                return coll.stream();
            } else {
                return Stream.of(arg);
            }
        }).toArray(Object[]::new);
    }

    private static void wrapSqlByCount(StringBuilder sql) {
        sql.insert(0, "SELECT count(*) cnt0 FROM (" + NL);
        sql.append(") t0");
    }

    @Override
    public SqlAndArgs buildInsertSql(FromPhrase p) {
        mayConvertAllParamNamesToSnakeCase(p);
        SqlSecurityUtils.checkTableNameLegality(p.table);
        StringBuilder sql0 = new StringBuilder();
        sql0.append("INSERT INTO ");
        sql0.append(nq).append(p.table).append(nq);
        if (p.afCols == null) {
            if (p.afVal.getMap() != null) { // auto generate colsAffected
                Set<String> colSet = new HashSet<>(p.afVal.getMap().keySet());
                p.afCols = colSet.toArray(new String[colSet.size()]);
            } else {
                throw new ChainedJdbcTemplateException(
                        "lack 'affect' phrase for INSERT SQL building while 'val' is not a Map.");
            }

        }

        sql0.append('(').append(joinNamesByComma(p.afCols, nq)).append(')');
        sql0.append(BL).append(dlmt).append("VALUES");
        sql0.append(CriteriaUtils.generateQMarkArrStrWithComma(p.afCols.length));

        Object[] args = buildNullableArgsOfAffectClause(p);
        SqlAndArgs sa = new SqlAndArgs(sql0.toString(), args);
        return sa;
    }

    @Override
    public SqlAndArgs buildUpdateSql(FromPhrase p) {
        mayConvertAllParamNamesToSnakeCase(p);
        SqlSecurityUtils.checkTableNameLegality(p.table);
        Object[] args = null;
        StringBuilder sql = new StringBuilder();
        // PRINT>>>> UPDATE ... SET ...
        sql.append("UPDATE ");
        sql.append(nq).append(p.table).append(nq).append(" SET ");
        // colsAffected auto-generation by afValMap
        if (p.afCols == null) {
            if (p.afVal.getMap() != null) {
                Set<String> colSet = new HashSet<>(p.afVal.getMap().keySet());
                if (p.byCols != null) { // remove colsBy from colsAffected
                    for (String c : p.byCols) {
                        colSet.remove(c);
                    }
                }
                p.afCols = colSet.toArray(new String[colSet.size()]);
            } else {
                throw new ChainedJdbcTemplateException(
                        "lack 'affect' phrase for UPDATE SQL building while 'val' is not a Map.");
            }

        }
        for (int i = 0, n = p.afCols.length; i < n; i++) {
            sql.append(nq).append(p.afCols[i]).append(nq).append("=?");
            if (i != n - 1) {
                sql.append(CM);
            }
        }
        // PRINT>>>> WHERE...
        Object[] argsToSet = buildNullableArgsOfAffectClause(p);
        ClauseAndArgs wherePart = buildWhereClauseAndArgs(p);
        if (!conf.isModifyAllRowsEnabled() && StringUtils.isEmpty(wherePart.getClause())) {
            throw new ChainedJdbcTemplateException("UPDATE without WHERE clause disallowed.");
        }
        sql.append(wherePart.getClause());
        args = ArrayUtils.addAll(argsToSet, wherePart.getArgs());
        SqlAndArgs sa = new SqlAndArgs(sql.toString(), args);
        return sa;
    }

    @Override
    public SqlAndArgs buildDeleteSql(FromPhrase p) {
        mayConvertAllParamNamesToSnakeCase(p);
        SqlSecurityUtils.checkTableNameLegality(p.table);
        StringBuilder sql = new StringBuilder();
        // PRINT>>>> DELETE FROM ...
        sql.append("DELETE FROM ");
        sql.append(nq).append(p.table).append(nq);
        // PRINT>>>> WHERE...
        ClauseAndArgs wherePart = buildWhereClauseAndArgs(p);
        if (!conf.isModifyAllRowsEnabled() && StringUtils.isEmpty(wherePart.getClause())) {
            throw new ChainedJdbcTemplateException("DELETE without WHERE clause disallowed.");
        }
        sql.append(wherePart.getClause());
        SqlAndArgs sa = new SqlAndArgs(sql.toString(), wherePart.getArgs());
        return sa;
    }

    private static String joinNamesByComma(String[] names, String nq) {
        for (String name : names) {
            SqlSecurityUtils.checkColumnNameLegality(name);
        }
        return nq + String.join(nq + CM + nq, names) + nq;
    }

    /**
     * exist side-effect
     */
    private static void convertToSnakeCase(String[] cols) {
        if (cols == null) {
            return;
        }
        for (int i = 0; i < cols.length; i++) {
            cols[i] = StringCases.camelToSnake(cols[i]);
        }
    }

    /**
     * exist side-effect
     */
    private static void convertValMapToSnakeCase(ValueHolder valueHolder) {
        if (valueHolder == null) {
            return;
        }
        //update valMap
        valueHolder.valMap(toSnakeCase(valueHolder.getMap()));
    }

    private static Map<String, Object> toSnakeCase(Map<String, Object> valMap) {
        if (valMap == null) {
            return null;
        }
        return Maps.rebuild(valMap,StringCases::camelToSnake, Function.identity());
    }

    private void mayConvertAllParamNamesToSnakeCase(FromPhrase p) {
        if (!conf.isConvertParamNameToSnakeCaseEnabled()) {
            return;
        }
        convertToSnakeCase(p.seCols);
        convertToSnakeCase(p.afCols);
        convertToSnakeCase(p.byCols);
        convertValMapToSnakeCase(p.val);
        convertValMapToSnakeCase(p.afVal);
        if (p.criteria != null) {
            for (Criterion criterion : p.criteria) {
                criterion.setName(StringCases.camelToSnake(criterion.getName()));
            }
        }
        if (p.sortingFields != null) {
            for (SortingField sortingField : p.sortingFields) {
                sortingField.setName(StringCases.camelToSnake(sortingField.getName()));
            }
        }


    }

    private static String buildOrderByClause(SortingField sf, String nq) {
        if (sf == null) {
            return null;
        }
        SqlSecurityUtils.checkColumnNameLegality(sf.getName());
        return nq + sf.getName() + nq + (sf.isDesc() ? " DESC" : "");
    }

}

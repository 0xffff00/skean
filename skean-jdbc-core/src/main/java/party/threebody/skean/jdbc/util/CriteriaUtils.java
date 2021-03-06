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

import org.apache.commons.lang3.StringUtils;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Criterion;
import party.threebody.skean.data.query.Operator;
import party.threebody.skean.data.query.SortingField;
import party.threebody.skean.jdbc.ChainedJdbcTemplateException;

import java.util.Collection;

public class CriteriaUtils {
    public static SortingField[] toSortingFields(String[] cols) {
        if (cols != null) {
            int n = cols.length;
            SortingField[] res = new SortingField[n];
            for (int i = 0; i < n; i++) {
                res[i] = toSortingField(cols[i]);
            }
            return res;
        }
        return null;
    }

    public static SortingField toSortingField(String col) {
        if (StringUtils.isNotEmpty(col)) {
            if (col.charAt(0) == '!' || col.charAt(0) == '-') {
                return new SortingField(col.substring(1), true);
            } else if (col.endsWith(" desc") || col.endsWith(" DESC")) {
                int len = col.length();
                return new SortingField(col.substring(0, len - 5), true);
            } else {
                return new SortingField(col);
            }
        }
        return null;
    }

    public static ClausesAndArgs toClausesAndArgs(Criterion[] criteria) {
        int n = criteria.length;
        int pNum = 0;
        ClauseAndArgs[] clauseAndParamsArr = new ClauseAndArgs[n];
        String[] clauses = new String[n];

        for (int i = 0; i < n; i++) {
            clauseAndParamsArr[i] = toClauseAndArgs(criteria[i]);
            clauses[i] = clauseAndParamsArr[i].getClause();
            pNum += clauseAndParamsArr[i].getArgs().length;
        }
        Object[] args = new Object[pNum];
        for (int i = 0, j = 0; i < n; i++) {
            for (int k = 0; k < clauseAndParamsArr[i].getArgs().length; k++) {
                args[j++] = clauseAndParamsArr[i].getArgs()[k];
            }
        }
        return new ClausesAndArgs(clauses, args);

    }

    public static ClauseAndArgs toClauseAndArgs(Criterion criterion) {
        if (criterion instanceof BasicCriterion) {
            return toClauseAndArgs((BasicCriterion) criterion);
        }
        throw new ChainedJdbcTemplateException("Unsupported Criterion Impl yet");

    }

    private static Operator toOperator(String optName) {
        if (optName == null) {
            return Operator.EQ;
        }
        try {
            return Operator.valueOf(optName);
        } catch (IllegalArgumentException e) {
            throw new ChainedJdbcTemplateException("Illegal Operator [" + optName + "]");
        }
    }

    public static ClauseAndArgs toClauseAndArgs(BasicCriterion criterion) {
        SqlSecurityUtils.checkColumnNameLegality(criterion.getName());
        // handle 'opt'
        String name = criterion.getName();
        Operator opt = toOperator(criterion.getOperator());
        Object val = criterion.getValue();
        Object[] valArr = null;


        String part0 = name;
        String part1;
        String part2 = "?";
        switch (opt) {
            case LE:
                part1 = "<=";
                break;
            case GE:
                part1 = ">=";
                break;
            case LT:
                part1 = "<";
                break;
            case GT:
                part1 = ">";
                break;
            case EQ:
                part1 = "=";
                // handle null value
                if (val == null) {
                    part1 = " IS ";
                    part2 = "NULL";
                    valArr = new Object[]{};
                }
                break;
            case NE:
                part1 = "!=";
                // handle null value
                if (val == null) {
                    part1 = " IS NOT ";
                    part2 = "NULL";
                    valArr = new Object[]{};
                }
                break;
            case IS:
                part1 = " IS ";
                part2 = "NULL";
                valArr = new Object[]{};
                break;
            case ISNOT:
                part1 = " IS NOT ";
                part2 = "NULL";
                valArr = new Object[]{};
                break;
            case K:
                part1 = " LIKE ";
                val = escapePercentSymbolAndWrap(val, "%", "%");
                break;
            case KR:
                part1 = " LIKE ";
                val = escapePercentSymbolAndWrap(val, "%", "");
                break;
            case KL:
                part1 = " LIKE ";
                val = escapePercentSymbolAndWrap(val, "", "%");
                break;
            case NK:
                part1 = " NOT LIKE ";
                val = escapePercentSymbolAndWrap(val, "%", "%");
                break;
            case NKR:
                part1 = " NOT LIKE ";
                val = escapePercentSymbolAndWrap(val, "%", "");
                break;
            case NKL:
                part1 = " NOT LIKE ";
                val = escapePercentSymbolAndWrap(val, "", "%");
                break;
            case IN:
                part1 = " IN ";
                if (val instanceof Collection) {
                    Collection<?> valColl = (Collection<?>) val;
                    int len = valColl.size();
                    part2 = generateQMarkArrStrWithComma(len);
                    valArr = valColl.toArray(new Object[len]);
                } else {  // regard as a singleton collection
                    part2 = "(?)";
                    valArr = new Object[]{val};
                }
                break;
            case NIN:
                part1 = " NOT IN ";
                if (val instanceof Collection) {
                    Collection<?> valColl = (Collection<?>) val;
                    int len = ((Collection<?>) val).size();
                    part2 = generateQMarkArrStrWithComma(len);
                    valArr = valColl.toArray(new Object[len]);
                } else {  // regard as a singleton collection
                    part2 = "(?)";
                    valArr = new Object[]{val};
                }
                break;
            default:
                throw new ChainedJdbcTemplateException("Unknown Operator [" + opt + "]");

        }
        // set default valArr.
        if (valArr == null) {
            valArr = new Object[]{val};
        }
        return new ClauseAndArgs(part0 + part1 + part2, valArr);
    }

    /**
     * generateQMarkArrStrWithComma(4)=="(?,?,?,?)"
     *
     * @param paramNum
     * @return
     */
    public static String generateQMarkArrStrWithComma(int paramNum) {
        StringBuilder sb = new StringBuilder("(");

        for (; paramNum > 1; paramNum--) {
            sb.append("?,");
        }
        if (paramNum == 1) {
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();
    }

    private static Object escapePercentSymbolAndWrap(Object val, String head, String tail) {
        if (val == null || !(val instanceof String)) {
            return val;
        }
        String s = (String) val;
        // most RDBMS supports
        return head + s.replaceAll("%", "[%]") + tail;

    }

    /**
     * build a clause like 'col IN ('a','b')' or an always false clause if values empty
     */
    public static String buildClauseOfInStrs(String columnName, Collection<String> values) {
        return buildClauseOfIn(columnName, values, "'");
    }

    public static String buildClauseOfInNums(String columnName, Collection<? extends Number> values) {
        return buildClauseOfIn(columnName, values, "");
    }

    private static String buildClauseOfIn(String columnName, Collection<?> values, String valDlmt) {
        if (values == null || values.isEmpty()) {
            return "'" + columnName + " IN'='NOTHING'";    //always false
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append(columnName).append(" IN (");
            for (Object v : values) {
                sb.append(valDlmt).append(v).append(valDlmt).append(",");
            }
            sb.deleteCharAt(sb.length() - 1).append(")");
            return sb.toString();
        }
    }

}

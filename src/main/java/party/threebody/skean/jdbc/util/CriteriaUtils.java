package party.threebody.skean.jdbc.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import party.threebody.skean.core.query.BasicCriterion;
import party.threebody.skean.core.query.Criterion;
import party.threebody.skean.core.query.SortingField;
import party.threebody.skean.jdbc.ChainedJdbcTemplateException;

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
			clauses[i] = clauseAndParamsArr[i].clause;
			pNum += clauseAndParamsArr[i].args.length;
		}
		Object[] args = new Object[pNum];
		for (int i = 0, j = 0; i < n; i++) {
			for (int k = 0; k < clauseAndParamsArr[i].args.length; k++) {
				args[j++] = clauseAndParamsArr[i].args[k];
			}
		}
		return new ClausesAndArgs(clauses, args);

	}
	public static ClauseAndArgs toClauseAndArgs(Criterion criterion) {
		if (criterion instanceof BasicCriterion){
			return toClauseAndArgs((BasicCriterion)criterion);
		}
		throw new ChainedJdbcTemplateException("unsupport Criterion Impl yet");
		
	}
	public static ClauseAndArgs toClauseAndArgs(BasicCriterion criterion) {
		if (!SqlSecurityUtils.checkNameLegality(criterion.getName())) {
			throw new ChainedJdbcTemplateException("illegal column name: " + criterion.getName());
		}
		// handle 'opt'
		String name = criterion.getName();
		String opt = criterion.getOperator();
		Object val = criterion.getValue();
		Object[] valArr = null;
		if (opt == null) {
			opt = "=";
		}

		String part0 = name;
		String part1 = opt;
		String part2 = "?";
		switch (opt) {
		case "<":
		case ">":
		case "<=":
		case ">=":
			break;
		case "=":
			// handle null value
			if (val == null) {
				part1 = " IS ";
				part2 = "NULL";
			}
			break;
		case "!=":
			// handle null value
			if (val == null) {
				part1 = " IS NOT ";
				part2 = "NULL";
			}
			break;
		case "~":
		case "contains":
			part1 = " LIKE ";
			val = escapePercentSymbolAndWrap(val, "%", "%");
			break;
		case "$":
		case "endsWith":
			part1 = " LIKE ";
			val = escapePercentSymbolAndWrap(val, "%", "");
			break;
		case "^":
		case "startsWith":
			part1 = " LIKE ";
			val = escapePercentSymbolAndWrap(val, "", "%");
			break;
		case "in":
			part1 = " IN ";
			if (val instanceof Collection) {
				Collection<?> valColl = (Collection<?>) val;
				int len = valColl.size();
				part2 = generateQMarkArrStrWithComma(len);
				valArr = valColl.toArray(new Object[len]);
			}
			break;
		case "not in":
			part1 = " NOT IN ";
			if (val instanceof Collection) {
				Collection<?> valColl = (Collection<?>) val;
				int len = ((Collection<?>) val).size();
				part2 = generateQMarkArrStrWithComma(len);
				valArr = valColl.toArray(new Object[len]);
			}
			break;
		default:
			throw new ChainedJdbcTemplateException("illegal 'opt': " + opt);

		}
		if (valArr == null) {
			if ("?".equals(part2)){
				valArr = new Object[] { val };
			}else{
				valArr = new Object[] { };
			}
			
		}
		return new ClauseAndArgs(part0 + part1 + part2, valArr);
	}

	/**
	 * generateQMarkArrStrWithComma(4)=="(?,?,?,?)"
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

}

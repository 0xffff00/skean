package party.threebody.skean.jdbc.phrase;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import party.threebody.skean.core.query.SortingField;
import party.threebody.skean.jdbc.ChainedJdbcTemplateException;

public class SqlBuilderMysqlImpl implements SqlBuilder {

	static Logger logger = LoggerFactory.getLogger(SqlBuilderMysqlImpl.class);
	private SqlBuilderConfig conf;

	final static char BL = ' '; // Blank
	final static char BQ = '`'; // Back Quote
	final static char NL = '\n'; // New Line
	final static char CM = ','; // Comma
	final static char SC = ';'; // Semicolon

	private String dlmt;
	private String nq;

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
		StringBuilder sql0 = new StringBuilder();
		Object[] args;
		String sels = "*";
		if (p.colsAffected != null) {
			throw new ChainedJdbcTemplateException("illegal phrase 'affect' for SELECT SQL building.");
		}
		// PRINT>>>> SELECT... FROM...
		// Handle 'select()'
		if (p.colsSelected != null) {
			sels = joinNamesByComma(p.colsSelected, nq);
		} else if (p.enableCount) {
			sels = "count(*) cnt"; // simple counting col
		}
		sql0.append("SELECT ").append(sels).append(dlmt).append("FROM ").append(nq).append(p.table).append(nq);
		if (p.enableCount) {
			if (p.tableAlias == null && p.colsSelected == null) {
				p.tableAlias = "t";
			}
		}
		if (p.tableAlias != null) {
			sql0.append(BL).append(p.tableAlias).append(dlmt);
		}

		// PRINT>>>> WHERE...
		sql0.append(buildWhereClause(p));
		// Handle 'count()'
		if (p.enableCount) {
			if (p.colsSelected != null) {
				// wrap sql with 'count'
				wrapSqlByCount(sql0);
			}
		}

		// Handle 'orderBy()'
		if (ArrayUtils.isNotEmpty(p.sortingFields)) {
			sql0.append(BL).append(dlmt).append("ORDER BY ");
			for (int i = 0, n = p.sortingFields.length; i < n; i++) {
				String oc = buildOrderByClause(p.sortingFields[i]);
				sql0.append(oc);
				if (i != n - 1) {
					sql0.append(CM);
				}
			}
		}

		// Handle 'page()'
		if (p.limit > 0) {

			sql0.append(BL).append(dlmt).append("LIMIT ");
			if (p.offset > 0) {
				sql0.append(p.offset).append(CM);
			}
			sql0.append(p.limit);
		}

		args = buildArgs(p);
		// return the result
		SqlAndArgs sa = new SqlAndArgs(sql0.toString(), args);
		logger.info(">>>>> final SQL >>>>>>>>>>>\n{}\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", sa.toANSIString2());
		return sa;

	}

	private String buildWhereClause(FromPhrase p) {
		StringBuilder sql0 = new StringBuilder();
		// (1/3)Handle 'by()'
		if (ArrayUtils.isNotEmpty(p.colsBy)) {
			// check col names' legality
			for (int i = 0, n = p.colsBy.length; i < n; i++) {
				if (!SqlSecurityUtils.checkNameLegality(p.colsBy[i])) {
					throw new ChainedJdbcTemplateException("illegal column name via by(): " + p.colsBy[i]);
				}
			}
			
			for (int i = 0, n = p.colsBy.length; i < n; i++) {
				sql0.append("  AND ").append(nq).append(p.colsBy[i]).append(nq).append("=?").append(dlmt);
			}
		
			
		}

		// (2/3)Handle 'criteria()' & fill 'valArr'
		if (p.criteria != null) {
			ClausesAndArgs cp = CriteriaUtils.toClausesAndArgs(p.criteria);
			if (ArrayUtils.isNotEmpty(cp.clauses)) {
				for (int i = 0, n = cp.clauses.length; i < n; i++) {
					sql0.append("  AND ").append(cp.clauses[i]).append(dlmt);
				}
			}
			p.valArr = cp.args; // filling
		}

		// (3/3)Handle 'where()'
		if (ArrayUtils.isNotEmpty(p.whereClauses)) {
			for (int i = 0, n = p.whereClauses.length; i < n; i++) {
				sql0.append("  AND ").append(p.whereClauses[i]).append(dlmt);
			}
		}
		if (sql0.length() > 5) {
			sql0.replace(0, 5, dlmt + "WHERE");
		}
		return sql0.toString();
	}

	private static void wrapSqlByCount(StringBuilder sql) {
		sql.insert(0, "SELECT count(*) cnt0 FROM (" + NL);
		sql.append(") t0");
	}

	@Override
	public SqlAndArgs buildInsertSql(FromPhrase p) {
		StringBuilder sql0 = new StringBuilder();
		sql0.append("INSERT INTO ");
		sql0.append(nq).append(p.table).append(nq);
		if (p.colsAffected == null) {
			if (p.valMap != null) { // auto generate colsAffected
				Set<String> colSet = new HashSet<String>(p.valMap.keySet());
				p.colsAffected = colSet.toArray(new String[colSet.size()]);
			} else {
				throw new ChainedJdbcTemplateException(
						"lack 'affect' phrase for INSERT SQL building while 'val' is not a Map.");
			}

		}

		sql0.append('(').append(joinNamesByComma(p.colsAffected, nq)).append(')');
		sql0.append(BL).append(dlmt).append("VALUES");
		sql0.append(CriteriaUtils.generateQMarkArrStrWithComma(p.colsAffected.length));

		Object[] args = buildArgs(p);
		SqlAndArgs sa = new SqlAndArgs(sql0.toString(), args);
		logger.info(">>>>> final SQL >>>>>>>>>>>\n{}\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", sa.toANSIString2());
		return sa;
	}

	@Override
	public SqlAndArgs buildUpdateSql(FromPhrase p) {
		Object[] args = null;
		StringBuilder sql0 = new StringBuilder();
		// PRINT>>>> UPDATE ... SET ...
		sql0.append("UPDATE ");
		sql0.append(nq).append(p.table).append(nq).append(" SET ");
		// colsAffected auto-generation by afValMap
		if (p.colsAffected == null) {
			if (p.afValMap != null) {
				Set<String> colSet = new HashSet<String>(p.afValMap.keySet());
				if (p.colsBy != null) { // remove colsBy from colsAffected
					for (String c : p.colsBy) {
						colSet.remove(c);
					}
				}
				p.colsAffected = colSet.toArray(new String[colSet.size()]);
			} else {
				throw new ChainedJdbcTemplateException(
						"lack 'affect' phrase for UPDATE SQL building while 'val' is not a Map.");
			}

		}
		for (int i = 0, n = p.colsAffected.length; i < n; i++) {
			sql0.append(nq).append(p.colsAffected[i]).append(nq).append("=?");
			if (i != n - 1) {
				sql0.append(CM);
			}
		}
		// PRINT>>>> WHERE...
		String wherePart = buildWhereClause(p);
		if (!conf.isModifyAllRowsEnabled() && StringUtils.isEmpty(wherePart)) {
			throw new ChainedJdbcTemplateException("UPDATE without WHERE clause disallowed.");
		}
		sql0.append(wherePart);
		args = buildArgs(p);
		SqlAndArgs sa = new SqlAndArgs(sql0.toString(), args);
		logger.info(">>>>> final SQL >>>>>>>>>>>\n{}\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", sa.toANSIString2());
		return sa;
	}

	@Override
	public SqlAndArgs buildDeleteSql(FromPhrase p) {
		Object[] args = null;
		StringBuilder sql0 = new StringBuilder();
		// PRINT>>>> DELETE FROM ...
		sql0.append("DELETE FROM ");
		sql0.append(nq).append(p.table).append(nq);
		// PRINT>>>> WHERE...
		String wherePart = buildWhereClause(p);
		if (!conf.isModifyAllRowsEnabled() && StringUtils.isEmpty(wherePart)) {
			throw new ChainedJdbcTemplateException("DELETE without WHERE clause disallowed.");
		}
		sql0.append(wherePart);
		args = buildArgs(p);
		SqlAndArgs sa = new SqlAndArgs(sql0.toString(), args);
		logger.info(">>>>> final SQL >>>>>>>>>>>\n{}\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", sa.toANSIString2());
		return sa;
	}

	private static Object[] buildArgs(FromPhrase p) {
		Object[] res = null;
		if (p.afValEnabled) {
			if (p.afValArr != null) {
				res = ArrayUtils.addAll(res, p.afValArr);
			}
			if (p.afValMap != null) {
				res = ArrayUtils.addAll(res, MapUtils.getValsArr(p.afValMap, p.colsAffected));
			}
			if (p.afValObj != null) {
				// TODO afValObj parsing
			}

		}

		if (p.valEnabled) {
			if (p.valArr != null) {
				res = ArrayUtils.addAll(res, p.valArr);
			}
			if (p.valMap != null) {
				if (!p.afValEnabled){
					res = ArrayUtils.addAll(res, MapUtils.getValsArr(p.valMap, p.colsAffected));
				}
				res = ArrayUtils.addAll(res, MapUtils.getValsArr(p.valMap, p.colsBy));
			}
			if (p.valObj != null) {
				// TODO valObj parsing
			}
		}

		return res;
	}

	private static String joinNamesByComma(String[] names, String nq) {
		return nq + String.join(nq + CM + nq, names) + nq;
	}

	public String buildOrderByClause(SortingField sf) {
		if (sf == null) {
			return null;
		}
		// TODO sql injection prevention
		return nq + sf.getName() + nq + (sf.isDesc() ? " DESC" : "");
	}

}

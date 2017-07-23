package party.threebody.skean.jdbc.phrase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import party.threebody.skean.core.query.SortingField;
import party.threebody.skean.jdbc.ChainedJdbcTemplateException;
import party.threebody.skean.jdbc.util.ArrayAndMapUtils;
import party.threebody.skean.jdbc.util.ClauseAndArgs;
import party.threebody.skean.jdbc.util.ClausesAndArgs;
import party.threebody.skean.jdbc.util.CriteriaUtils;
import party.threebody.skean.jdbc.util.ReflectionUtils;
import party.threebody.skean.jdbc.util.SqlAndArgs;
import party.threebody.skean.jdbc.util.SqlSecurityUtils;

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
		StringBuilder sql = new StringBuilder();
		String sels = "*";
		if (p.afCols != null) {
			throw new ChainedJdbcTemplateException("illegal phrase 'affect' for SELECT SQL building.");
		}
		// PRINT>>>> SELECT... FROM...
		// Handle 'select()'
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

		// Handle 'page()'
		if (p.limit > 0) {
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

			Object[] nullableArgs = buildNullableArgsOfWhereClause(p);
			args = ArrayAndMapUtils.toNonNullArr(nullableArgs);
			// consider null value situation
			for (int i = 0, n = p.byCols.length; i < n; i++) {
				sql.append(dlmt).append("  AND ").append(nq).append(p.byCols[i]).append(nq);
				if (nullableArgs[i] == null) {
					sql.append(" IS NULL");
				} else {
					sql.append("=?");
				}
				sql.append(BL);
			}
		}

		// (3/3)Handle 'where()'
		if (p.whereClauses != null) {
			args = buildNullableArgsOfWhereClause(p);
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
				return ReflectionUtils.getProperties(p.afVal.getObj(), p.afCols);
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
				return ReflectionUtils.getProperties(p.val.getObj(), p.afCols);
			}
		}
		throw new ChainedJdbcTemplateException("build args of 'INSERT' or 'UPDATE' clause failed. ");
	}

	private static Object[] buildNullableArgsOfWhereClause(FromPhrase p) {
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

					if (p.byCols != null) { // handle:
											// 'affect(...).by(...).val(...)'
						if (p.afCols.length + p.byCols.length != p.val.getArr().length) {
							throw new ChainedJdbcTemplateException(
									"amount of 'args' & 'colsA+colsB' in 'affect(colsA).by(colsB).val(args)' mismatched.");
						}

					}
					if (p.whereClauses != null) { // handle:
													// 'affect(...).where(...).val(...)'
						// nothing to check
					}
					// consider not all byVal is used to 'by()',but also to
					// 'affect()'
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
			return ReflectionUtils.getProperties(p.val.getObj(), p.byCols);
		}
		throw new ChainedJdbcTemplateException("build args of 'WHERE' clause failed. ");
	}

	private static void wrapSqlByCount(StringBuilder sql) {
		sql.insert(0, "SELECT count(*) cnt0 FROM (" + NL);
		sql.append(") t0");
	}

	@Override
	public SqlAndArgs buildInsertSql(FromPhrase p) {
		SqlSecurityUtils.checkTableNameLegality(p.table);
		StringBuilder sql0 = new StringBuilder();
		sql0.append("INSERT INTO ");
		sql0.append(nq).append(p.table).append(nq);
		if (p.afCols == null) {
			if (p.val.getMap() != null) { // auto generate colsAffected
				Set<String> colSet = new HashSet<String>(p.val.getMap().keySet());
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
		SqlSecurityUtils.checkTableNameLegality(p.table);
		Object[] args = null;
		StringBuilder sql = new StringBuilder();
		// PRINT>>>> UPDATE ... SET ...
		sql.append("UPDATE ");
		sql.append(nq).append(p.table).append(nq).append(" SET ");
		// colsAffected auto-generation by afValMap
		if (p.afCols == null) {
			if (p.afVal.getMap() != null) {
				Set<String> colSet = new HashSet<String>(p.afVal.getMap().keySet());
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

	private static String buildOrderByClause(SortingField sf, String nq) {
		if (sf == null) {
			return null;
		}
		SqlSecurityUtils.checkColumnNameLegality(sf.getName());
		return nq + sf.getName() + nq + (sf.isDesc() ? " DESC" : "");
	}

}

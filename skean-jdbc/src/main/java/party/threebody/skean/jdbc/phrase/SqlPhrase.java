package party.threebody.skean.jdbc.phrase;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;

import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.util.SqlAndArgs;

public class SqlPhrase extends DefaultRootPhrase {

	static Logger logger = LoggerFactory.getLogger(SqlPhrase.class);

	String sql;

	Object argObj;
	Map<String, Object> argMap;
	Object[] args;

	public SqlPhrase(ChainedJdbcTemplateContext context, String sql) {
		super();
		this.context = context;
		this.sql = sql;
	}

	// ------ args value filling --------
	public ArgPhrase argArr(Object[] vals) {
		this.args = vals;
		return new ArgPhrase(this);
	}

	public ArgPhrase argMap(Map<String, Object> vals) {
		this.argMap = vals;
		return new ArgPhrase(this);
	}

	public ArgPhrase argObj(Object vals) {
		this.argObj = vals;
		return new ArgPhrase(this);
	}

	@SuppressWarnings("unchecked")
	public ArgPhrase arg(Object... val) {
		if (val == null || val.length == 0) {
			return new ArgPhrase(this);
		}
		if (val.length == 1) {
			Object v0 = val[0];
			if (v0 instanceof Map) {
				return argMap((Map<String, Object>) v0);
			}
			if (!(v0 instanceof Number) && !(v0 instanceof String)) {
				return argObj(v0);
			}
		}
		return argArr(val);
	}

	// ------ fetching --------
	
	@Override
	protected SqlAndArgs buildSelectSqlAndArgs() {
		return new SqlAndArgs(sql,args);
	}
	
	

	// ------ modifying --------

	public int execute() {
		context.getSqlPrinter().printSql(sql, args);
		int rna = context.getJdbcTemplate().update(sql, new ArgumentPreparedStatementSetter(args));
		context.getSqlPrinter().printRowNumAffected(rna);
		return rna;

	}

	

}
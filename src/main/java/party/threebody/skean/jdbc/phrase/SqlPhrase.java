package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;

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
	public List<Map<String, Object>> list() {
		context.getSqlPrinter().printSql(sql, args);
		List<Map<String, Object>> res = context.getJdbcTemplate().query(sql, args, context.getColumnMapRowMapper());
		context.getSqlPrinter().printResultList(res);
		return res;
	}

	@Override
	public <T> List<T> list(Class<T> elementType) {
		context.getSqlPrinter().printSql(sql, args);
		List<T> res = context.getJdbcTemplate().query(sql, args, new BeanPropertyRowMapper<T>(elementType));
		context.getSqlPrinter().printResultList(res);
		return res;
	}

	@Override
	public <T> List<T> list(RowMapper<T> rowMapper) {
		context.getSqlPrinter().printSql(sql, args);
		List<T> res = context.getJdbcTemplate().query(sql, args, rowMapper);
		context.getSqlPrinter().printResultList(res);
		return res;
	}

	public <T> T single(Class<T> elementType) {
		context.getSqlPrinter().printSql(sql, args);
		T res = context.getJdbcTemplate().queryForObject(sql, args, elementType);
		context.getSqlPrinter().printResultBean(res);
		return res;
	}

	// ------ modifying --------

	protected int update() {
		context.getSqlPrinter().printSql(sql, args);
		int rna = context.getJdbcTemplate().update(sql, new ArgumentPreparedStatementSetter(args));
		context.getSqlPrinter().printRowNumAffected(rna);
		return rna;

	}

}
package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
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
	protected ArgPhrase argArr(Object[] vals) {
		this.args = vals;
		return new ArgPhrase(this);
	}

	protected ArgPhrase argMap(Map<String, Object> vals) {
		this.argMap = vals;
		return new ArgPhrase(this);
	}

	protected ArgPhrase argObj(Object vals) {
		this.argObj = vals;
		return new ArgPhrase(this);
	}

	@SuppressWarnings("unchecked")
	protected ArgPhrase arg(Object... val) {
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
		return context.getJdbcTmpl().query(sql, args, context.getColumnMapRowMapper());
	}

	@Override
	public <T> List<T> list(Class<T> elementType) {
		return context.getJdbcTmpl().queryForList(sql, elementType, args);
	}

	@Override
	public <T> List<T> list(RowMapper<T> rowMapper) {
		return context.getJdbcTmpl().query(sql, args, rowMapper);
	}

	public <T> T single(Class<T> elementType) {
		return context.getJdbcTmpl().queryForObject(sql, args, elementType);
	}

	// ------ modifying --------

	protected int update() {
		return context.getJdbcTmpl().update(sql, new ArgumentPreparedStatementSetter(args));

	}

}
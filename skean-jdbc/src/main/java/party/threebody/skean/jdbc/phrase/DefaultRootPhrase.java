package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.DualColsBean;
import party.threebody.skean.jdbc.DualColsBeanRowMapper;
import party.threebody.skean.jdbc.util.SqlAndArgs;

public abstract class DefaultRootPhrase implements RootPhrase {

	boolean enableCount;
	ChainedJdbcTemplateContext context;

	// ------ fetching --------
	public List<Map<String, Object>> list() {
		return list(context.getColumnMapRowMapper());
	}

	public <T> List<T> list(Class<T> elementType) {
		return list(new BeanPropertyRowMapper<T>(elementType));
	}

	public <T> List<T> list(RowMapper<T> rowMapper) {
		SqlAndArgs sa = buildSelectSqlAndArgs();
		return listInternal(sa.getSql(), sa.getArgs(), rowMapper);
	}

	public <T> List<T> listOfSingleCol(Class<T> columnType) {
		return list(SingleColumnRowMapper.newInstance(columnType));
	}

	public <F0,F1> List<DualColsBean<F0,F1>> listOfDualCols(Class<F0> col0Clazz,Class<F1> col1Clazz){
		return list(new DualColsBeanRowMapper<>(col0Clazz,col1Clazz));
	}

	protected abstract SqlAndArgs buildSelectSqlAndArgs();

	protected <T> List<T> listInternal(String sql, Object[] args, RowMapper<T> rowMapper) {
		context.getSqlPrinter().printSql(sql, args);
		List<T> res = context.getJdbcTemplate().query(sql, args, rowMapper);
		context.getSqlPrinter().printResultList(res);
		return res;
	}

	/**
	 * {@link org.springframework.jdbc.core.JdbcTemplate#queryForObject} not
	 * supported.<br>
	 * 
	 * 1 row expected.
	 *
	 * @throws EmptyResultDataAccessException
	 *             if no element at all has been found in the given Collection
	 * @param elementType
	 * @return the unique result from the unique row
	 */
	public <T> T single(Class<T> elementType) {
		List<T> results = list(elementType);
		return DataAccessUtils.requiredSingleResult(results);
	}

	/**
	 * 1 row expected.
	 * 
	 * @return
	 */
	public Map<String, Object> single() {
		SqlAndArgs sa = buildSelectSqlAndArgs();
		context.getSqlPrinter().printSql(sa);
		Map<String, Object> res = context.getJdbcTemplate().queryForMap(sa.getSql(), sa.getArgs());
		context.getSqlPrinter().printResultBean(res);
		return res;
	}

	protected static Object firstValueInFirstOfMap(List<Map<String, Object>> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0).entrySet().iterator().next().getValue();
	}

	protected static <T> T firstElementOrNull(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Map<String, Object> first() {
		return firstElementOrNull(list());
	}

	@Override
	public <T> T first(Class<T> elementType) {
		return firstElementOrNull(list(elementType));
	}

	@Override
	public <T> T first(RowMapper<T> rowMapper) {
		return firstElementOrNull(list(rowMapper));
	}

	@Override
	public Object firstCell() {
		List<Map<String, Object>> list = list();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0).entrySet().iterator().next().getValue();
	}

	@Override
	public int count() {
		enableCount = true;
		Object obj = firstCell();
		if (obj == null) {
			return COUNT_NOTHING;
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else {
			return COUNT_UNAVAILABLE;
		}

	}

}

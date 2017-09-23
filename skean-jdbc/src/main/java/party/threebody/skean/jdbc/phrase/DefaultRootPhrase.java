package party.threebody.skean.jdbc.phrase;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.rs.DualColsBean;
import party.threebody.skean.jdbc.rs.DualColsBeanRowMapper;
import party.threebody.skean.jdbc.rs.TripleColsBean;
import party.threebody.skean.jdbc.rs.TripleColsBeanRowMapper;
import party.threebody.skean.jdbc.util.SqlAndArgs;

import java.util.List;
import java.util.Map;

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

    public <F0, F1> List<DualColsBean<F0, F1>> listOfDualCols(Class<F0> type0, Class<F1> type1) {
        return list(new DualColsBeanRowMapper<>(type0, type1));
    }

    public <F0, F1, F2> List<TripleColsBean<F0, F1, F2>> listOfTripleCols(Class<F0> type0, Class<F1> type1, Class<F2> type2) {
        return list(new TripleColsBeanRowMapper<>(type0, type1, type2));
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
     * <p>
     * 1 row expected.
     *
     * @param elementType
     * @return the unique result from the unique row
     * @throws EmptyResultDataAccessException if no element at all has been found in the given Collection
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

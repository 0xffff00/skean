package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.util.Maps;
import party.threebody.skean.util.ObjectMappers;

/**
 * 
 * @author hzk
 * @since 2017-08-01
 * @param <T>
 *            type of the entity bean
 * @param <PK>
 *            type of the primary key(s)
 */
public abstract class AbstractCrudDAO<T, PK> implements GenericCrudDAO<T, PK> {

	@Autowired
	ChainedJdbcTemplate cjt;

	protected abstract String getTable();

	protected abstract Class<T> getBeanClass();

	protected abstract List<String> getPrimaryKeyColumns();

	protected abstract List<String> getAffectedColumns();

	private FromPhrase fromTable() {
		return cjt.from(getTable());
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> convertBeanToMap(Object bean) {
		Map<String, Object> wholeMap = ObjectMappers.OM_SNAKE_CASE.convertValue(bean, Map.class);
		if (getAffectedColumns() != null) {
			return Maps.rebuild(wholeMap, getAffectedColumns());
		} else {
			return wholeMap;
		}
	}

	@Override
	public T create(T entity) {
		Map<String, Object> propsMap = convertBeanToMap(entity);
		fromTable().affect(propsMap).insert();
		return fromTable().by(getPKColsArr()).val(propsMap).limit(1).first(getBeanClass());

	}

	@Override
	public List<T> readList(QueryParamsSuite qps) {
		return fromTable().suite(qps).list(getBeanClass());
	}

	@Override
	public int readCount(QueryParamsSuite qps) {
		return fromTable().suite(qps).count();
	}

	@Override
	public T readOne(PK pk) {
		return fromTable().by(getPKColsArr()).val(pk).limit(1).first(getBeanClass());
	}

	@Override
	public int update(T entity, PK pk) {
		Map<String, Object> propsMap = convertBeanToMap(entity);
		return fromTable().affect(propsMap).by(getPKColsArr()).val(pk).update();
	}

	@Override
	public int delete(PK pk) {
		return fromTable().by(getPKColsArr()).val(pk).delete();
	}

	private String[] getPKColsArr() {
		return getPrimaryKeyColumns().toArray(new String[0]);
	}
}

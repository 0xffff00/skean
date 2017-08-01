package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.phrase.FromPhrase;

/**
 * 
 * @author hzk
 * @since 2017-08-01
 * @param <T>
 *            type of the entity bean
 * @param <PK>
 *            type of the primary key(s)
 */
@Service
public abstract class SimpleBeanCrudService<T, PK> implements SingleBeanCrudService<T, PK> {

	@Autowired
	ChainedJdbcTemplate cjt;

	protected abstract String getTable();

	protected abstract Class<T> getBeanClass();

	protected abstract String[] getPkCol();

	private FromPhrase fromTable() {
		return cjt.from(getTable());
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> convertBeanToMap(Object bean) {
		return ObjectMappers.OM_SNAKE_CASE.convertValue(bean, Map.class);
	}

	@Override
	public T create(T entity) {
		Map<String, Object> propsMap = convertBeanToMap(entity);
		fromTable().affect(propsMap).insert();
		return fromTable().by(getPkCol()).val(propsMap).single(getBeanClass());

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
		return fromTable().by(getPkCol()).val(pk).single(getBeanClass());
	}

	@Override
	public int update(T entity, PK pk) {
		Map<String, Object> propsMap = convertBeanToMap(entity);
		return fromTable().affect(propsMap).by(getPkCol()).val(pk).update();
	}

	@Override
	public int delete(PK pk) {
		return fromTable().by(getPkCol()).val(pk).delete();
	}

}

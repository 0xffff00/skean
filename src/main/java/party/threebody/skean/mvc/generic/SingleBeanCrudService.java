package party.threebody.skean.mvc.generic;

import java.util.List;

import party.threebody.skean.core.query.QueryParamsSuite;

/**
 * 
 * @author hzk
 * @since 2017-08-01
 * @param <T>
 *            type of the entity bean
 * @param <PK>
 *            type of the primary key(s)
 */
public interface SingleBeanCrudService<T, PK> {

	T create(T entity);

	List<T> readList(QueryParamsSuite qps);

	int readCount(QueryParamsSuite qps);

	T readOne(PK pk);

	int update(T entity, PK pk);

	int delete(PK pk);

}

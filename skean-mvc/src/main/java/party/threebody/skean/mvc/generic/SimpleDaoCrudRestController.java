package party.threebody.skean.mvc.generic;

import org.springframework.beans.factory.annotation.Autowired;
import party.threebody.skean.core.query.QueryParamsSuite;

import java.util.List;

/**
 * 
 * @author hzk
 * @since 2017-08-02
 * @param <T>
 * @param <PK>
 */
public abstract class SimpleDaoCrudRestController<T, PK> extends AbstractCrudRestController<T, PK> {

	protected abstract GenericCrudDAO<T, PK> getCrudDAO();

	@Autowired
	GenericCrudDaoTemplateService crudDaoTemplateService;

	@Override
	protected T create(T entity) {
		return crudDaoTemplateService.create(getCrudDAO(), entity);
	}

	@Override
	protected List<T> readList(QueryParamsSuite qps) {
		return crudDaoTemplateService.readList(getCrudDAO(), qps);
	}

	@Override
	protected int readCount(QueryParamsSuite qps) {
		return crudDaoTemplateService.readCount(getCrudDAO(), qps);
	}

	@Override
	protected T readOne(PK pk) {
		return crudDaoTemplateService.readOne(getCrudDAO(), pk);
	}

	@Override
	protected int update(T entity, PK pk) {
		return crudDaoTemplateService.update(getCrudDAO(), entity, pk);
	}

	@Override
	protected int delete(PK pk) {
		return crudDaoTemplateService.delete(getCrudDAO(), pk);
	}

}

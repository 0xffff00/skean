package party.threebody.skean.mvc.generic;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import party.threebody.skean.core.query.QueryParamsSuite;

/**
 * a convenient class to wrap GenericCrudDAO, supplying transaction to feed
 * upper layer.
 * 
 * @author hzk
 * @since 2017-08-02
 */
@Service
public class GenericCrudDaoTemplateService {

	@Transactional(propagation = Propagation.REQUIRED)
	public <T, PK> T create(GenericCrudDAO<T, PK> dao, T entity) {
		return dao.create(entity);
	}

	public <T, PK> List<T> readList(GenericCrudDAO<T, PK> dao, QueryParamsSuite qps) {
		return dao.readList(qps);
	}

	public <T, PK> int readCount(GenericCrudDAO<T, PK> dao, QueryParamsSuite qps) {
		return dao.readCount(qps);
	}

	public <T, PK> T readOne(GenericCrudDAO<T, PK> dao, PK pk) {
		return dao.readOne(pk);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public <T, PK> int update(GenericCrudDAO<T, PK> dao, T entity, PK pk) {
		return dao.update(entity, pk);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public <T, PK> int delete(GenericCrudDAO<T, PK> dao, PK pk) {
		return dao.delete(pk);
	}

}

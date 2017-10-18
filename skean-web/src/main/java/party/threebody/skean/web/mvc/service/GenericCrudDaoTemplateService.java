package party.threebody.skean.web.mvc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.web.mvc.dao.SinglePKCrudDAO;

import java.util.List;

/**
 * a convenient class to wrap GenericCrudDAO, supplying transaction to feed
 * upper layer.
 * <p>
 * not so useful
 *
 * @author hzk
 * @since 2017-08-02
 */
@Service
public class GenericCrudDaoTemplateService {

    @Transactional(propagation = Propagation.REQUIRED)
    public <T, PK> T create(SinglePKCrudDAO<T, PK> dao, T entity) {
        return dao.createAndGet(entity);
    }

    public <T, PK> List<T> readList(SinglePKCrudDAO<T, PK> dao, QueryParamsSuite qps) {
        return dao.readList(qps);
    }

    public <T, PK> int readCount(SinglePKCrudDAO<T, PK> dao, QueryParamsSuite qps) {
        return dao.readCount(qps);
    }

    public <T, PK> T readOne(SinglePKCrudDAO<T, PK> dao, PK pk) {
        return dao.readOne(pk);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <T, PK> int update(SinglePKCrudDAO<T, PK> dao, T entity, PK pk) {
        return dao.update(entity, pk);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <T, PK> int delete(SinglePKCrudDAO<T, PK> dao, PK pk) {
        return dao.delete(pk);
    }

}

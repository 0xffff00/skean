package party.threebody.skean.web.mvc.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @param <T>  type of the entity bean
 * @param <PK> type of the primary key
 * @author hzk
 * @since 2017-08-01
 */
public abstract class SinglePKCrudDAO<T, PK> extends AbstractCrudDAO<T> {

    /**
     * @return name of the only column which is exact primary key
     */
    protected abstract String getPrimaryKeyColumn();

    @Override
    protected final List<String> getPrimaryKeyColumns() {
        return Collections.singletonList(getPrimaryKeyColumn());
    }

    public T readOne(PK pk) {
        return readOne(new Object[]{pk});
    }

    public int update(T entity, PK pk) {
        return update(entity, new Object[]{pk});
    }

    /**
     * @since skean 2.0
     */
    public int partialUpdate(Map<String, Object> fieldsToUpdate, PK pk) {
        return partialUpdate(fieldsToUpdate, new Object[]{pk});
    }

    public int delete(PK pk) {
        return delete(new Object[]{pk});
    }

}

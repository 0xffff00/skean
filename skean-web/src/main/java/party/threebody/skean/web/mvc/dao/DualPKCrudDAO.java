package party.threebody.skean.web.mvc.dao;

import java.util.Map;

/**
 * @param <T>   type of the entity bean
 * @param <PK1> type of the first primary key
 * @param <PK2> type of the second primary key
 * @author hzk
 * @since 2017-09-02
 */
public abstract class DualPKCrudDAO<T, PK1, PK2> extends AbstractCrudDAO<T> {

    public T readOne(PK1 pk1, PK2 pk2) {
        return readOne(new Object[]{pk1, pk2});
    }


    public int update(T entity, PK1 pk1, PK2 pk2) {
        return update(entity, new Object[]{pk1, pk2});
    }

    /**
     * @since skean 2.0
     */
    public int partialUpdate(Map<String, Object> fieldsToUpdate, PK1 pk1, PK2 pk2) {
        return partialUpdate(fieldsToUpdate, new Object[]{pk1, pk2});
    }

    public int delete(PK1 pk1, PK2 pk2) {
        return delete(new Object[]{pk1, pk2});
    }
}

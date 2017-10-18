package party.threebody.skean.web.mvc.dao;

import java.util.Map;

/**
 * @param <T>   type of the entity bean
 * @param <PK1> type of the first primary key
 * @param <PK2> type of the second primary key
 * @param <PK3> type of the third primary key
 * @author hzk
 * @since 2017-09-02
 */
public abstract class TriplePKCrudDAO<T, PK1, PK2, PK3> extends AbstractCrudDAO<T> {

    public T readOne(PK1 pk1, PK2 pk2, PK3 pk3) {
        return readOne(new Object[]{pk1, pk2, pk3});
    }

    public int update(T entity, PK1 pk1, PK2 pk2, PK3 pk3) {
        return update(entity, new Object[]{pk1, pk2, pk3});
    }

    /**
     * @since skean 2.0
     */
    public int partialUpdate(Map<String, Object> fieldsToUpdate, PK1 pk1, PK2 pk2, PK3 pk3) {
        return partialUpdate(fieldsToUpdate, new Object[]{pk1, pk2, pk3});
    }

    public int delete(PK1 pk1, PK2 pk2, PK3 pk3) {
        return delete(new Object[]{pk1, pk2, pk3});
    }
}

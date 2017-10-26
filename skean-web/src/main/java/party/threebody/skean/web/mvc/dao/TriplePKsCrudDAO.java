package party.threebody.skean.web.mvc.dao;


import java.util.Arrays;
import java.util.Map;

/**
 * @param <E>   type of the entity bean
 * @param <PK1> type of the first primary key
 * @param <PK2> type of the second primary key
 * @param <PK3> type of the third primary key
 * @author hzk
 * @since 2017-09-02
 */
public interface TriplePKsCrudDAO<E, PK1, PK2, PK3> extends MultiPKsCrudDAO<E> {

    default E readOne(PK1 pk1, PK2 pk2, PK3 pk3) {
        return readOne(Arrays.asList(pk1, pk2, pk3));
    }


    default int update(E entity, PK1 pk1, PK2 pk2, PK3 pk3) {
        return update(entity, Arrays.asList(pk1, pk2, pk3));
    }

    /**
     * @since skean 2.0
     */
    default int partialUpdate(Map<String, Object> fieldsToUpdate, PK1 pk1, PK2 pk2, PK3 pk3) {
        return partialUpdate(fieldsToUpdate, Arrays.asList(pk1, pk2, pk3));
    }

    default int delete(PK1 pk1, PK2 pk2, PK3 pk3) {
        return delete(Arrays.asList(pk1, pk2, pk3));
    }
}

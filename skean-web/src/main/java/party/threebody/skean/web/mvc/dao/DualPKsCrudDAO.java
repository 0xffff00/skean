package party.threebody.skean.web.mvc.dao;


import java.util.Arrays;
import java.util.Map;

/**
 * @param <E>   type of the entity bean
 * @param <PK1> type of the first primary key
 * @param <PK2> type of the second primary key
 * @author hzk
 * @since 2017-09-02
 * @since skean 2.0
 */
public interface DualPKsCrudDAO<E, PK1, PK2> extends MultiPKsCrudDAO<E> {

    default E readOne(PK1 pk1, PK2 pk2) {
        return readOne(Arrays.asList(pk1, pk2));
    }


    default int update(E entity, PK1 pk1, PK2 pk2) {
        return update(entity, Arrays.asList(pk1, pk2));
    }

    /**
     * @since skean 2.0
     */
    default int partialUpdate(Map<String, Object> fieldsToUpdate, PK1 pk1, PK2 pk2) {
        return partialUpdate(fieldsToUpdate, Arrays.asList(pk1, pk2));
    }

    default int delete(PK1 pk1, PK2 pk2) {
        return delete(Arrays.asList(pk1, pk2));
    }
}

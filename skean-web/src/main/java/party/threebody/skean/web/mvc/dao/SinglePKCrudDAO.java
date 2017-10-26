package party.threebody.skean.web.mvc.dao;


import java.util.Arrays;
import java.util.Map;

public interface SinglePKCrudDAO<E, PK> extends MultiPKsCrudDAO<E> {

    // name of the only column which is the very primary key
    default String getPrimaryKeyColumn() {
        if (getPrimaryKeyColumns() == null) {
            return null;
        }
        return getPrimaryKeyColumns().get(0);
    }

    default E readOne(PK pk) {
        return readOne(Arrays.asList(pk));
    }

    default int update(E entity, PK pk) {
        return update(entity, Arrays.asList(pk));
    }

    /**
     * @since skean 2.0
     */
    default int partialUpdate(Map<String, Object> fieldsToUpdate, PK pk) {
        return partialUpdate(fieldsToUpdate, Arrays.asList(pk));
    }

    default int delete(PK pk) {
        return delete(Arrays.asList(pk));
    }

}

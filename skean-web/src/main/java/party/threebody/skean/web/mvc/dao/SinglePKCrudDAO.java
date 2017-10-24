package party.threebody.skean.web.mvc.dao;


import java.util.Map;

public interface SinglePKCrudDAO<E, PK> extends PrimaryKeysAwareCrudDAO<E> {

    // name of the only column which is the very primary key
    default String getPrimaryKeyColumn() {
        if (getPrimaryKeyColumns() == null) {
            return null;
        }
        return getPrimaryKeyColumns().get(0);
    }

    default E readOne(PK pk) {
        return readOne(new Object[]{pk});
    }

    default int update(E entity, PK pk) {
        return update(entity, new Object[]{pk});
    }

    /**
     * @since skean 2.0
     */
    default int partialUpdate(Map<String, Object> fieldsToUpdate, PK pk) {
        return partialUpdate(fieldsToUpdate, new Object[]{pk});
    }

    default int delete(PK pk) {
        return delete(new Object[]{pk});
    }

}

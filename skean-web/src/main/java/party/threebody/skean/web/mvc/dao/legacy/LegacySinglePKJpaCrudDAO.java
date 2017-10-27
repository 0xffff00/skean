package party.threebody.skean.web.mvc.dao.legacy;

import party.threebody.skean.web.mvc.dao.JpaCrudDAO;
import party.threebody.skean.web.mvc.dao.SinglePKCrudDAO;
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO;

import java.util.Arrays;
import java.util.Map;

/**
 * {@link SinglePKJpaCrudDAO}'s abstract class version compatible with groovy compiler.<br>
 * while groovy do not support multiple extension to interfaces containing java 8 default methods.
 *
 * @param <E>
 * @param <PK>
 * @since 2.1
 */
public abstract class LegacySinglePKJpaCrudDAO<E, PK>  implements JpaCrudDAO<E> ,SinglePKCrudDAO<E, PK> {

    @Override
    // name of the only column which is the very primary key
    public String getPrimaryKeyColumn() {
        if (getPrimaryKeyColumns() == null) {
            return null;
        }
        return getPrimaryKeyColumns().get(0);
    }

    public E readOne(PK pk) {
        return readOne(Arrays.asList(pk));
    }

    public int update(E entity, PK pk) {
        return update(entity, Arrays.asList(pk));
    }

    public int partialUpdate(Map<String, Object> fieldsToUpdate, PK pk) {
        return partialUpdate(fieldsToUpdate, Arrays.asList(pk));
    }

    public int delete(PK pk) {
        return delete(Arrays.asList(pk));
    }
}

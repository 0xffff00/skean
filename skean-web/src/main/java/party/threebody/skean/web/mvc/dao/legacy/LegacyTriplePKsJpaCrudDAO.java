package party.threebody.skean.web.mvc.dao.legacy;

import party.threebody.skean.web.mvc.dao.JpaCrudDAO;
import party.threebody.skean.web.mvc.dao.TriplePKsCrudDAO;
import party.threebody.skean.web.mvc.dao.TriplePKsJpaCrudDAO;

/**
 * {@link TriplePKsJpaCrudDAO}'s abstract class version compatible with groovy compiler.<br>
 * while groovy do not support multiple extension to interfaces containing java 8 default methods.
 *
 * @param <E>
 * @param <PK1>
 * @param <PK2>
 * @param <PK3>
 */
public abstract class LegacyTriplePKsJpaCrudDAO<E, PK1, PK2, PK3>
        implements JpaCrudDAO<E> ,TriplePKsCrudDAO<E, PK1, PK2, PK3> {
}

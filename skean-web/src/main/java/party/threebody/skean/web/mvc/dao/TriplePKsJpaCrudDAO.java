package party.threebody.skean.web.mvc.dao;

/**
 * NOTICE: cann't pass in groovy compiler
 * @param <E>
 * @param <PK1>
 * @param <PK2>
 * @param <PK3>
 */
public abstract class TriplePKsJpaCrudDAO<E, PK1, PK2, PK3>
        implements TriplePKsCrudDAO<E, PK1, PK2, PK3>, JpaCrudDAO<E> {

}

package party.threebody.skean.web.mvc.dao;

/**
 * NOTICE: cann't pass in groovy compiler
 * @param <E>
 * @param <PK1>
 * @param <PK2>
 * @since skean 2.0
 */
public abstract class DualPKsJpaCrudDAO<E, PK1, PK2> implements DualPKsCrudDAO<E, PK1, PK2>, JpaCrudDAO<E> {


}

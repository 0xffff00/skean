package party.threebody.skean.web.mvc.dao;

/**
 * NOTICE: cann't pass in groovy compiler
 *
 * @param <E>
 * @param <PK>
 * @since 2.1
 */
public abstract class SinglePKJpaCrudDAO<E, PK> implements SinglePKCrudDAO<E, PK>, JpaCrudDAO<E> {

}

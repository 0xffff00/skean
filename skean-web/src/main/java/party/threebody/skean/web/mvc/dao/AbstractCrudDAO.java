package party.threebody.skean.web.mvc.dao;

import org.apache.commons.collections4.CollectionUtils;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.jdbc.util.JavaBeans;
import party.threebody.skean.lang.StringCases;

import java.util.*;


/**
 * <h1>an abstact DAO(Data Access Object) providing basic CRUD methods.</h1>
 * AbstractCrudDAO uses {@link ChainedJdbcTemplate},which holding the very {@link javax.sql.DataSource}.<br>
 * AbstractCrudDAO concerns about a single entity bean, usually a domain object.
 *
 * @param <E> type of the entity bean
 * @author hzk
 * @since 2017-10-22
 * @since 2.0
 */
public interface AbstractCrudDAO<E> {

    ChainedJdbcTemplate getChainedJdbcTemplate();

    String getTable();

    Class<E> getEntityClass();

    /**
     * if return null, build actual AffectedColumns by properties of the bean
     *
     * @return names of columns which should be affected (insert or update)
     */
    List<String> getAffectedColumns();

    default List<String> getInsertedColumns() {
        return getAffectedColumns();
    }

    default List<String> getUpdatedColumns() {
        return getAffectedColumns();
    }


    default Map<String, Object> convertEntityBeanToMap(E entity) {
        return JavaBeans.convertBeanToSimpleMap(entity, StringCases::camelToSnake);

    }

    default Map<String, Object> buildExtraValMapToInsert(E entity) {
        return Collections.emptyMap();

    }

    default Map<String, Object> buildExtraValMapToUpdate(E entity) {
        return Collections.emptyMap();
    }

    default FromPhrase fromTable() {
        return getChainedJdbcTemplate().from(getTable());
    }


    default int create(E entity) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToInsert(entity));
        return fromTable().affect(getInsertedColumns()).val(propsMap).insert();
    }

    default List<E> readList(CriteriaAndSortingAndPaging csp) {
        return fromTable().criteriaAndSortAndPage(csp).list(getEntityClass());
    }

    default int readCount(Criteria c) {
        return fromTable().criteria(c).count();
    }

    default int updateSome(E entity, Criteria criteria) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        return fromTable().affect(getUpdatedColumns()).val(propsMap).criteria(criteria).update();
    }

    /**
     * @since skean 2.1
     */
    default int partialUpdateSome(Map<String, Object> fieldsToUpdate, Criteria c) {
        if (fieldsToUpdate.isEmpty()) {
            return 0;
        }
        Map<String, Object> propsMap = new HashMap<>(fieldsToUpdate);
        propsMap.putAll(buildExtraValMapToUpdate(null));
        Collection<String> afCols = CollectionUtils.intersection(propsMap.keySet(), getUpdatedColumns());
        return fromTable().affect(afCols).valMap(propsMap).criteria(c).update();
    }

    default int deleteSome(Criteria c) {
        return fromTable().criteria(c).delete();
    }

}

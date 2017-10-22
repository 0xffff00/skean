package party.threebody.skean.web.mvc.dao;

import org.apache.commons.collections4.CollectionUtils;
import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.phrase.ByPhrase;
import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.jdbc.util.JavaBeans;
import party.threebody.skean.lang.StringCases;

import java.util.*;


/**
 * @param <E> type of the entity bean
 * @author hzk
 * @since 2017-10-22
 */
public interface AbstractCrudDAO<E> {

    ChainedJdbcTemplate getChainedJdbcTemplate();

    String getTable();

    Class<E> getEntityClass();

    /**
     * @return names of columns which are exact primary keys
     */
    List<String> getPrimaryKeyColumns();

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

    default ByPhrase fromTableByPkCols() {
        return getChainedJdbcTemplate().from(getTable()).by(getPrimaryKeyColumns());
    }


    default int create(E entity) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToInsert(entity));
        return fromTable().affect(getInsertedColumns()).val(propsMap).insert();
    }

    default E createAndGet(E entity) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToInsert(entity));
        fromTable().affect(getInsertedColumns()).val(propsMap).insert();
        return fromTable().by(getPrimaryKeyColumns()).val(propsMap).limit(1).first(getEntityClass());
    }

    default List<E> readList(QueryParamsSuite qps) {
        return fromTable().suite(qps).list(getEntityClass());
    }

    default int readCount(QueryParamsSuite qps) {
        return fromTable().suite(qps).count();
    }

    default E readOne(Object[] pk) {
        return fromTableByPkCols().valArr(pk).limit(1).first(getEntityClass());
    }

    default E readOneByExample(E example) {
        return fromTableByPkCols().valObj(example).limit(1).first(getEntityClass());
    }

    default int update(E entity, Object[] pkArr) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        return fromTable().affect(getUpdatedColumns()).val(propsMap).by(getPrimaryKeyColumns()).valArr(pkArr).update();
    }

    default int updateByExample(E entity) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        return fromTable().affect(getUpdatedColumns()).val(propsMap).by(getPrimaryKeyColumns()).valObj(entity).update();
    }

    /**
     * @since skean 2.0
     */
    default int partialUpdate(E entity, Object[] pkArr, Collection<String> colsToUpdate) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        Collection<String> afCols = CollectionUtils.intersection(colsToUpdate, getUpdatedColumns());
        return fromTable().affect(afCols).valMap(propsMap).by(getPrimaryKeyColumns()).valArr(pkArr).update();
    }

    /**
     * @since skean 2.0
     */
    default int partialUpdate(Map<String, Object> fieldsToUpdate, Object[] pkArr) {
        if (fieldsToUpdate.isEmpty()) {
            return 0;
        }
        Map<String, Object> propsMap = new HashMap<>(fieldsToUpdate);
        propsMap.putAll(buildExtraValMapToUpdate(null));
        Collection<String> afCols = CollectionUtils.intersection(propsMap.keySet(), getUpdatedColumns());
        return fromTable().affect(afCols).valMap(propsMap).by(getPrimaryKeyColumns()).valArr(pkArr).update();
    }

    default int delete(Object[] pkArr) {
        return fromTableByPkCols().val(pkArr).delete();
    }

    default int deleteByExample(E example) {
        return fromTableByPkCols().valObj(example).delete();
    }

    default int deleteSome(QueryParamsSuite qps) {
        return fromTable().suite(qps).delete();
    }
}

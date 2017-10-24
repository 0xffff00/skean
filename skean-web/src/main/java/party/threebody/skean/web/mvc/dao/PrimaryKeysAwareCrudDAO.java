package party.threebody.skean.web.mvc.dao;

import org.apache.commons.collections4.CollectionUtils;
import party.threebody.skean.jdbc.phrase.ByPhrase;

import java.util.*;


/**
 * @param <E> type of the entity bean
 * @author hzk
 * @since 2017-10-24
 */
public interface PrimaryKeysAwareCrudDAO<E> extends AbstractCrudDAO<E> {

    /**
     * @return names of columns which are exact primary keys
     */
    List<String> getPrimaryKeyColumns();

    default ByPhrase fromTableByPkCols() {
        return getChainedJdbcTemplate().from(getTable()).by(getPrimaryKeyColumns());
    }


    default E createAndGet(E entity) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToInsert(entity));
        fromTable().affect(getInsertedColumns()).val(propsMap).insert();
        return fromTable().by(getPrimaryKeyColumns()).val(propsMap).limit(1).first(getEntityClass());
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

}

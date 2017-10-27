/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.web.mvc.dao;

import org.apache.commons.collections4.CollectionUtils;
import party.threebody.skean.jdbc.phrase.ByPhrase;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @param <E> type of the entity bean
 * @author hzk
 * @since 2017-10-24
 * @since 2.1
 */
public interface MultiPKsCrudDAO<E> extends AbstractCrudDAO<E> {

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

    default E readOne(Collection<Object> primaryKeys) {
        return fromTableByPkCols().valArr(primaryKeys).limit(1).first(getEntityClass());
    }

    default E readOneByExample(E example) {
        return fromTableByPkCols().valObj(example).limit(1).first(getEntityClass());
    }

    default int update(E entity, Collection<Object> primaryKeys) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        return fromTable().affect(getUpdatedColumns()).val(propsMap)
                .by(getPrimaryKeyColumns()).valArr(primaryKeys).update();
    }

    default int updateByExample(E entity) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        return fromTable().affect(getUpdatedColumns()).val(propsMap)
                .by(getPrimaryKeyColumns()).valObj(entity).update();
    }


    default int partialUpdate(E entity, Collection<Object> primaryKeys, Collection<String> colsToUpdate) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        Collection<String> afCols = CollectionUtils.intersection(colsToUpdate, getUpdatedColumns());
        return fromTable().affect(afCols).valMap(propsMap).by(getPrimaryKeyColumns()).valArr(primaryKeys).update();
    }


    default int partialUpdate(Map<String, Object> fieldsToUpdate, Collection<Object> primaryKeys) {
        if (fieldsToUpdate.isEmpty()) {
            return 0;
        }
        Map<String, Object> propsMap = new HashMap<>(fieldsToUpdate);
        propsMap.putAll(buildExtraValMapToUpdate(null));
        Collection<String> afCols = CollectionUtils.intersection(propsMap.keySet(), getUpdatedColumns());
        return fromTable().affect(afCols).valMap(propsMap).by(getPrimaryKeyColumns()).valArr(primaryKeys).update();
    }

    default int delete(Collection<Object> primaryKeys) {
        return fromTableByPkCols().val(primaryKeys).delete();
    }

    default int deleteByExample(E example) {
        return fromTableByPkCols().valObj(example).delete();
    }

}

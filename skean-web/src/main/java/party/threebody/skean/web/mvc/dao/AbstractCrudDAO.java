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
     * @return all columns those should be persistent
     */
    List<String> getAllColumns();

    /**
     * default implementation is {@link #getAllColumns()}
     *
     * @return the white list of column names that can be in {@link Criteria}'s name for querying
     */
    default List<String> getLegalCriterialColumns() {
        return getAllColumns();
    }


    /**
     * default implementation is {@link #getAllColumns()}
     *
     * @return which columns should be affected when inserting
     */
    default List<String> getInsertableColumns() {
        return getAllColumns();
    }

    /**
     * default implementation is {@link #getAllColumns()}
     *
     * @return which columns should be affected when updating
     */
    default List<String> getUpdatableColumns() {
        return getAllColumns();
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

    default void ensureCriteriaLegal(Criteria criteria) {
        if (criteria == null) {
            return;
        }
        criteria.ensureAllNamesLegal(getLegalCriterialColumns());
    }



    default FromPhrase fromTable() {
        return getChainedJdbcTemplate().from(getTable());
    }


    default int create(E entity) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToInsert(entity));
        return fromTable().affect(getInsertableColumns()).val(propsMap).insert();
    }

    default List<E> readList(CriteriaAndSortingAndPaging csp) {
        ensureCriteriaLegal(csp);
        return fromTable().criteriaAndSortAndPage(csp).list(getEntityClass());
    }

    default int readCount(Criteria c) {
        ensureCriteriaLegal(c);
        return fromTable().criteria(c).count();
    }

    default int updateSome(E entity, Criteria criteria) {
        Map<String, Object> propsMap = convertEntityBeanToMap(entity);
        propsMap.putAll(buildExtraValMapToUpdate(entity));
        return fromTable().affect(getUpdatableColumns()).val(propsMap).criteria(criteria).update();
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
        Collection<String> afCols = CollectionUtils.intersection(propsMap.keySet(), getUpdatableColumns());
        return fromTable().affect(afCols).valMap(propsMap).criteria(c).update();
    }

    default int deleteSome(Criteria c) {
        return fromTable().criteria(c).delete();
    }

}

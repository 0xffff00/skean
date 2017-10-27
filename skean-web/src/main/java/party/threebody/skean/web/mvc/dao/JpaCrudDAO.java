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

import org.apache.commons.collections4.ListUtils;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationUtils;
import party.threebody.skean.data.CreateTime;
import party.threebody.skean.data.LastUpdateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @param <E>
 * @since 2.0
 */
public interface JpaCrudDAO<E> extends MultiPKsCrudDAO<E> {

    static boolean isAnnotatedOfAnyType(Field field, Class... annotationTypes) {
        return Stream.of(annotationTypes)
                .anyMatch(at ->
                        AnnotationUtils.findAnnotation(field, at) != null
                );
    }

    static Object now(Class<?> timeClass) {
        if (timeClass.equals(LocalDateTime.class)) {
            return LocalDateTime.now().toString();
        }
        //TODO support other types
        return null;
    }

    static <E> List<String> fetchFieldNamesByAnnotated(Class<E> entityClass, Class... annotationTypes) {
        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> isAnnotatedOfAnyType(field, annotationTypes))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    static <E> Map<String, Object> buildNowTimeMapByAnnotated(Class<E> entityClass, Class... annotationTypes) {
        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> isAnnotatedOfAnyType(field, annotationTypes))
                .collect(Collectors.toMap(
                        f -> f.getName(),
                        f -> now(f.getType())
                ));
    }

    @Override
    default String getTable() {
        Table annoTable = getEntityClass().getAnnotation(Table.class);
        if (annoTable != null) {
            return annoTable.name();
        }
        return null;
    }

    @Override
    default Class<E> getEntityClass() {
        return (Class<E>) GenericTypeResolver.resolveTypeArgument(getClass(), JpaCrudDAO.class);
    }

    @Override
    default List<String> getPrimaryKeyColumns() {
        return Stream.of(getEntityClass().getDeclaredFields())
                .filter(f -> f.getAnnotation(Id.class) != null)
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    @Override
    default List<String> getAffectedColumns() {
        return fetchFieldNamesByAnnotated(getEntityClass(), Id.class, Column.class);
    }

    @Override
    default List<String> getInsertedColumns() {
        return ListUtils.union(
                getAffectedColumns(),
                fetchFieldNamesByAnnotated(getEntityClass(), CreateTime.class, LastUpdateTime.class));
    }

    @Override
    default List<String> getUpdatedColumns() {
        return ListUtils.union(
                getAffectedColumns(),
                fetchFieldNamesByAnnotated(getEntityClass(), LastUpdateTime.class));
    }

    @Override
    default Map<String, Object> buildExtraValMapToInsert(E entity) {
        return buildNowTimeMapByAnnotated(getEntityClass(), CreateTime.class, LastUpdateTime.class);
    }

    @Override
    default Map<String, Object> buildExtraValMapToUpdate(E entity) {
        return buildNowTimeMapByAnnotated(getEntityClass(), LastUpdateTime.class);
    }
}

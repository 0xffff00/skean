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


import java.util.Arrays;
import java.util.Map;

/**
 * @param <E>
 * @param <PK>
 * @since 2.0
 */
public interface SinglePKCrudDAO<E, PK> extends MultiPKsCrudDAO<E> {

    // name of the only column which is the very primary key
    default String getPrimaryKeyColumn() {
        if (getPrimaryKeyColumns() == null) {
            return null;
        }
        return getPrimaryKeyColumns().get(0);
    }

    default E readOne(PK pk) {
        return readOne(Arrays.asList(pk));
    }

    default int update(E entity, PK pk) {
        return update(entity, Arrays.asList(pk));
    }

    default int partialUpdate(Map<String, Object> fieldsToUpdate, PK pk) {
        return partialUpdate(fieldsToUpdate, Arrays.asList(pk));
    }

    default int delete(PK pk) {
        return delete(Arrays.asList(pk));
    }

}

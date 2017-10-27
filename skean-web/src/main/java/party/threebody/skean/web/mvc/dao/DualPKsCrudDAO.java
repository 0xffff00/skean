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
 * @param <E>   type of the entity bean
 * @param <PK1> type of the first primary key
 * @param <PK2> type of the second primary key
 * @author hzk
 * @since 2017-09-02
 * @since skean 2.0
 */
public interface DualPKsCrudDAO<E, PK1, PK2> extends MultiPKsCrudDAO<E> {

    default E readOne(PK1 pk1, PK2 pk2) {
        return readOne(Arrays.asList(pk1, pk2));
    }


    default int update(E entity, PK1 pk1, PK2 pk2) {
        return update(entity, Arrays.asList(pk1, pk2));
    }

    /**
     * @since skean 2.0
     */
    default int partialUpdate(Map<String, Object> fieldsToUpdate, PK1 pk1, PK2 pk2) {
        return partialUpdate(fieldsToUpdate, Arrays.asList(pk1, pk2));
    }

    default int delete(PK1 pk1, PK2 pk2) {
        return delete(Arrays.asList(pk1, pk2));
    }
}

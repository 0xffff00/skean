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

package party.threebody.skean.web.mvc.dao.legacy;

import party.threebody.skean.web.mvc.dao.DualPKsCrudDAO;
import party.threebody.skean.web.mvc.dao.DualPKsJpaCrudDAO;
import party.threebody.skean.web.mvc.dao.JpaCrudDAO;
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO;

import java.util.Arrays;
import java.util.Map;

/**
 * {@link SinglePKJpaCrudDAO}'s abstract class version compatible with groovy compiler.<br>
 * while groovy do not support multiple extension to interfaces containing java 8 default methods.
 *
 * @param <E>
 * @param <PK1>
 * @param <PK2>
 * @since 2.1
 */
public abstract class LegacyDualPKsJpaCrudDAO<E, PK1, PK2> implements JpaCrudDAO<E>,
        DualPKsCrudDAO<E, PK1, PK2> {

    public E readOne(PK1 pk1, PK2 pk2) {
        return readOne(Arrays.asList(pk1, pk2));
    }


    public int update(E entity, PK1 pk1, PK2 pk2) {
        return update(entity, Arrays.asList(pk1, pk2));
    }

    public int partialUpdate(Map<String, Object> fieldsToUpdate, PK1 pk1, PK2 pk2) {
        return partialUpdate(fieldsToUpdate, Arrays.asList(pk1, pk2));
    }

    public int delete(PK1 pk1, PK2 pk2) {
        return delete(Arrays.asList(pk1, pk2));
    }
}

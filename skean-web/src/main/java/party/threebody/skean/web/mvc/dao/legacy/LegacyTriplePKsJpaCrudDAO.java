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

import party.threebody.skean.web.mvc.dao.JpaCrudDAO;
import party.threebody.skean.web.mvc.dao.TriplePKsCrudDAO;
import party.threebody.skean.web.mvc.dao.TriplePKsJpaCrudDAO;

/**
 * {@link TriplePKsJpaCrudDAO}'s abstract class version compatible with groovy compiler.<br>
 * while groovy do not support multiple extension to interfaces containing java 8 default methods.
 *
 * @param <E>
 * @param <PK1>
 * @param <PK2>
 * @param <PK3>
 */
public abstract class LegacyTriplePKsJpaCrudDAO<E, PK1, PK2, PK3>
        implements JpaCrudDAO<E> ,TriplePKsCrudDAO<E, PK1, PK2, PK3> {
}

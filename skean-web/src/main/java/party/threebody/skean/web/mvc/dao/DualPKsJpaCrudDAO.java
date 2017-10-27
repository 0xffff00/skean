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

/**
 * NOTICE: cann't pass in groovy compiler
 * @param <E>
 * @param <PK1>
 * @param <PK2>
 * @since skean 2.0
 */
public abstract class DualPKsJpaCrudDAO<E, PK1, PK2> implements DualPKsCrudDAO<E, PK1, PK2>, JpaCrudDAO<E> {


}

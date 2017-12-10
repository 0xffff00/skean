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

package party.threebody.skean.web.data;

import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;

import java.util.Collection;
import java.util.Map;

public interface CriteriaBuilder {
    /**
     * <h1>read parameters by all keys</h1>
     *
     * @param paramsMap a parameter Map
     * @return Criteria built
     */
    Criteria toCriteria(Map<String, Object> paramsMap);

    /**
     * <h1>read parameters by all keys</h1>
     *
     * @param paramsMap a parameter Map
     * @return CriteriaAndSortingAndPaging built
     */
    CriteriaAndSortingAndPaging toCriteriaAndSortingAndPaging(Map<String, Object> paramsMap);

    /**
     * <h1>read parameters by keys restricted by a white list</h1>
     *
     * @param paramsMap          a parameter Map
     * @param paramNamewhiteList white list to filter keys of the parameter Map, if null filter disabled
     * @return CriteriaAndSortingAndPaging built
     */
    CriteriaAndSortingAndPaging toCriteriaAndSortingAndPaging(Map<String, Object> paramsMap,
                                                              Collection<String> paramNamewhiteList);

    BasicCriterion[] buildBasicCriterionArray(Map<String, Object> paramsMap);

    BasicCriterion[] buildBasicCriterionArray(Map<String, Object> paramsMap,
                                              Collection<String> paramNameWhiteList);
}

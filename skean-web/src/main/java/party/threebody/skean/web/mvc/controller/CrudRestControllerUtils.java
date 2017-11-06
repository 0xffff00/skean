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

package party.threebody.skean.web.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.data.query.PLOxStyleCriteriaUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CrudRestControllerUtils {

    private static final String HEADER_NAME_TOTAL_COUNT = "X-Total-Count";
    private static final String HEADER_NAME_TOTAL_AFFECTED = "X-Total-Affected";

    private CrudRestControllerUtils() {

    }

    /**
     * respond for a controller method by PLOx
     *
     * @param varMap      reqestParams Map from a controller method
     * @param listReader  a function to fetch entities list by QPS
     * @param countReader a function to fetch count without QPS
     * @param <E>         the type of entity
     * @return what a controller method should return
     */
    public static <E> ResponseEntity<List<E>> respondListAndCount(
            Map<String, Object> varMap,
            Function<CriteriaAndSortingAndPaging, List<E>> listReader,
            Function<Criteria, Integer> countReader) {
        CriteriaAndSortingAndPaging csp = PLOxStyleCriteriaUtils.toCriteriaAndSortingAndPaging(varMap);
        return respondListAndCount(csp, listReader, countReader);
    }


    public static <E> ResponseEntity<List<E>> respondListAndCount(
            CriteriaAndSortingAndPaging criteriaAndSortingAndPaging,
            Function<CriteriaAndSortingAndPaging, List<E>> listReader,
            Function<Criteria, Integer> countReader) {

        List<E> entities = listReader.apply(criteriaAndSortingAndPaging);
        int totalCount = 0;
        if (criteriaAndSortingAndPaging.getPagingInfo().isPagingEnabled()) {
            totalCount = countReader.apply(criteriaAndSortingAndPaging);
        } else {
            totalCount = entities.size();
        }
        return ResponseEntity.ok().header(HEADER_NAME_TOTAL_COUNT, String.valueOf(totalCount)).body(entities);
    }

    public static ResponseEntity<Object> respondRowNumAffected(Integer rowNumAffected) {
        if (rowNumAffected == null || rowNumAffected == 0) {
            return new ResponseEntity<Object>(HttpStatus.GONE);
        }
        return ResponseEntity.noContent().header(HEADER_NAME_TOTAL_AFFECTED, String.valueOf(rowNumAffected)).build();
    }
}
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.web.SkeanForbiddenException;
import party.threebody.skean.web.data.CriteriaBuilder;
import party.threebody.skean.web.data.SkeanWebConfig;
import party.threebody.skean.web.mvc.MultiValueMaps;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @param <E>
 * @since 2.1
 * @see UriVarCrudRestController
 * @see MatrixVarCrudRestController
 */
public abstract class CrudRestController<E> {

    private CriteriaBuilder criteriaBuilder;

    private SkeanWebConfig skeanWebConfig;

    @Autowired
    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    @Autowired
    public void setSkeanWebConfig(SkeanWebConfig skeanWebConfig) {
        this.skeanWebConfig = skeanWebConfig;
    }

    public abstract void buildCrudFunctions(CrudFunctionsBuilder<E> builder);

    protected CrudFunctions<E> getCrudFunctions() {
        CrudFunctionsBuilder<E> builder = new CrudFunctionsBuilder<>();
        buildCrudFunctions(builder);
        return builder.build();
    }

    /**
     * HTTP GET
     */
    protected ResponseEntity<List<E>> httpReadList0(MultiValueMap<String, String> criteriaParams) {
        Map<String, Object> criteriaParamMap = MultiValueMaps.toMap(criteriaParams);
        return respondListAndCount(criteriaParamMap,
                getCrudFunctions().getListReader(), getCrudFunctions().getCountReader());
    }


    /**
     * return empty body because of unwareness of primary keys
     *
     * @param entity entity to create
     */
    @PostMapping("")
    public ResponseEntity<E> httpCreate(@RequestBody E entity) {
        getCrudFunctions().getOneCreator().apply(entity);
        return ResponseEntity.created(null).build();

    }

    /**
     * HTTP PUT
     */
    protected ResponseEntity<?> httpCreateOrUpdate0(MultiValueMap<String, String> criteriaParams, E entity) {
        Map<String, Object> criteriaParamMap = MultiValueMaps.toMap(criteriaParams);
        CriteriaAndSortingAndPaging csp = criteriaBuilder.toCriteriaAndSortingAndPaging(criteriaParamMap);
        Integer cnt = getCrudFunctions().getCountReader().apply(csp);
        if (cnt == 0) {
            //create if not exists
            return httpCreate(entity);
        }
        if (cnt > 1 && !getCrudFunctions().isBatchUpdateEnabled()) {
            throw new SkeanForbiddenException("batch update is disabled.");
        }
        //update if exists
        Integer rna = getCrudFunctions().getUpdater().apply(entity, csp);
        return respondRowNumAffected(rna);

    }

    /**
     * HTTP PATCH: partialUpdate
     */
    protected ResponseEntity<Object> httpPartialUpdate0(MultiValueMap<String, String> criteriaParams,
                                                        Map<String, Object> contentToApply) {
        Map<String, Object> criteriaParamMap = MultiValueMaps.toMap(criteriaParams);
        CriteriaAndSortingAndPaging csp = criteriaBuilder.toCriteriaAndSortingAndPaging(criteriaParamMap);
        Integer cnt = getCrudFunctions().getCountReader().apply(csp);
        if (cnt > 1 && !getCrudFunctions().isBatchUpdateEnabled()) {
            throw new SkeanForbiddenException("batch update is disabled.");
        }
        Integer rna = getCrudFunctions().getPartialUpdater().apply(contentToApply, csp);
        return respondRowNumAffected(rna);
    }

    /**
     * HTTP DELETE
     */
    protected ResponseEntity<Object> httpDelete0(MultiValueMap<String, String> criteriaParams) {
        Map<String, Object> criteriaParamMap = MultiValueMaps.toMap(criteriaParams);
        CriteriaAndSortingAndPaging csp = criteriaBuilder.toCriteriaAndSortingAndPaging(criteriaParamMap);
        Integer cnt = getCrudFunctions().getCountReader().apply(csp);
        if (cnt > 1 && !getCrudFunctions().isBatchDeleteEnabled()) {
            throw new SkeanForbiddenException("batch delete is disabled.");
        }
        Integer rna = getCrudFunctions().getDeleter().apply(csp);
        return respondRowNumAffected(rna);
    }

    protected <E> ResponseEntity<List<E>> respondListAndCount(
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
        return ResponseEntity.ok()
                .header(skeanWebConfig.getHeaderName().getTotalCount(), String.valueOf(totalCount)).body(entities);
    }

    protected ResponseEntity<Object> respondRowNumAffected(Integer rowNumAffected) {
        if (rowNumAffected == null || rowNumAffected == 0) {
            return new ResponseEntity<Object>(HttpStatus.GONE);
        }
        return ResponseEntity.noContent()
                .header(skeanWebConfig.getHeaderName().getTotalAffected(), String.valueOf(rowNumAffected)).build();
    }

    /**
     * respond for a controller method by PLOx
     *
     * @param varMap      reqestParams Map from a controller method
     * @param listReader  a function to fetch entities list by CriteriaAndSortingAndPaging
     * @param countReader a function to fetch count without CriteriaAndSortingAndPaging
     * @param <E>         the type of entity
     * @return what a controller method should return
     */
    protected <E> ResponseEntity<List<E>> respondListAndCount(
            Map<String, Object> varMap,
            Function<CriteriaAndSortingAndPaging, List<E>> listReader,
            Function<Criteria, Integer> countReader) {
        CriteriaAndSortingAndPaging csp = criteriaBuilder.toCriteriaAndSortingAndPaging(varMap);
        return respondListAndCount(csp, listReader, countReader);
    }
}


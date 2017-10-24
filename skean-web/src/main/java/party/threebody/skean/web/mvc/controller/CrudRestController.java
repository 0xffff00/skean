package party.threebody.skean.web.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.data.query.PLOxStyleCriteriaUtils;
import party.threebody.skean.web.SkeanForbiddenException;
import party.threebody.skean.web.mvc.MultiValueMaps;

import java.util.List;
import java.util.Map;

public abstract class CrudRestController<E> {

    public abstract void buildCrudFunctions(CrudFunctions.Builder<E> builder);

    protected CrudFunctions<E> getCrudFunctions() {
        CrudFunctions.Builder<E> builder = new CrudFunctions.Builder<>();
        buildCrudFunctions(builder);
        return builder.build();
    }

    /**
     * HTTP GET
     */
    protected ResponseEntity<List<E>> httpReadList0(MultiValueMap<String, String> criteriaParams) {
        Map<String, Object> criteriaParamMap = MultiValueMaps.toMap(criteriaParams);
        return ControllerUtils.respondListAndCount(criteriaParamMap,
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
        CriteriaAndSortingAndPaging csp = PLOxStyleCriteriaUtils.toCriteriaAndSortingAndPaging(criteriaParamMap);
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
        return ControllerUtils.respondRowNumAffected(rna);

    }

    /**
     * HTTP PATCH: partialUpdate
     */
    protected ResponseEntity<Object> httpPartialUpdate0(MultiValueMap<String, String> criteriaParams,
                                                     Map<String, Object> contentToApply) {
        Map<String, Object> criteriaParamMap = MultiValueMaps.toMap(criteriaParams);
        CriteriaAndSortingAndPaging csp = PLOxStyleCriteriaUtils.toCriteriaAndSortingAndPaging(criteriaParamMap);
        Integer cnt = getCrudFunctions().getCountReader().apply(csp);
        if (cnt > 1 && !getCrudFunctions().isBatchUpdateEnabled()) {
            throw new SkeanForbiddenException("batch update is disabled.");
        }
        Integer rna = getCrudFunctions().getPartialUpdater().apply(contentToApply, csp);
        return ControllerUtils.respondRowNumAffected(rna);
    }

    /**
     * HTTP DELETE
     */
    protected ResponseEntity<Object> httpDelete0(MultiValueMap<String, String> criteriaParams) {
        Map<String, Object> criteriaParamMap = MultiValueMaps.toMap(criteriaParams);
        CriteriaAndSortingAndPaging csp = PLOxStyleCriteriaUtils.toCriteriaAndSortingAndPaging(criteriaParamMap);
        Integer cnt = getCrudFunctions().getCountReader().apply(csp);
        if (cnt > 1 && !getCrudFunctions().isBatchDeleteEnabled()) {
            throw new SkeanForbiddenException("batch delete is disabled.");
        }
        Integer rna = getCrudFunctions().getDeleter().apply(csp);
        return ControllerUtils.respondRowNumAffected(rna);
    }
}


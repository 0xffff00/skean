package party.threebody.skean.web.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * a simple RestController supporting typical CRUD operations of an entity
 * bean<br>
 * all parameters are UriVar or PathVar
 *
 * @param <E> type of the entity bean
 * @author hzk
 * @since skean 2.1
 */
public abstract class UriVarCrudRestController<E> extends CrudRestController<E> {


    /**
     * HTTP GET
     */
    @GetMapping("")
    public ResponseEntity<List<E>> httpReadList(@RequestParam MultiValueMap<String, String> requestParamMap) {
        return httpReadList0(requestParamMap);
    }


    /**
     * HTTP PUT
     */
    @PutMapping("")
    public ResponseEntity<?> httpCreateOrUpdate(@RequestParam MultiValueMap<String, String> requestParamMap,
                                                @RequestBody E entity) {
        return httpCreateOrUpdate0(requestParamMap, entity);
    }

    /**
     * HTTP PATCH: partialUpdate
     */
    @PatchMapping("")
    public ResponseEntity<Object> httpPartialUpdate(@RequestParam MultiValueMap<String, String> requestParamMap,
                                                    Map<String, Object> contentToApply) {
        return httpPartialUpdate0(requestParamMap, contentToApply);
    }


    /**
     * HTTP DELETE
     */
    @DeleteMapping("")
    public ResponseEntity<Object> httpDelete(@RequestParam MultiValueMap<String, String> requestParamMap) {
        return httpDelete0(requestParamMap);
    }
}


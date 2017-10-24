package party.threebody.skean.web.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * a simple RestController supporting typical CRUD operations of an entity
 * bean<br>
 * ,which contains a single-column primary key.
 *
 * @param <E> type of the entity bean
 * @author hzk
 * @since skean 2.1
 * @since 2017-10-24
 */
public abstract class MatrixVarCrudRestController<E> extends CrudRestController<E> {

    @GetMapping("/{matrixVars}")
    public ResponseEntity<List<E>> httpReadList(
            @MatrixVariable(pathVar = "matrixVars") MultiValueMap<String, String> matrixVars) {
        return httpReadList0(matrixVars);
    }

    @PutMapping("/{matrixVars}")
    public ResponseEntity<?> httpCreateOrUpdate(
            @MatrixVariable(pathVar = "matrixVars") MultiValueMap<String, String> matrixVars,
            @RequestBody E entity) {
        return httpCreateOrUpdate0(matrixVars, entity);
    }

    @PatchMapping("/{matrixVars}")
    public ResponseEntity<Object> httpPartialUpdate(
            @MatrixVariable(pathVar = "matrixVars") MultiValueMap<String, String> matrixVars,
            @RequestBody Map<String, Object> requestParamMap) {
        return httpPartialUpdate0(matrixVars, requestParamMap);
    }

    @DeleteMapping("/{matrixVars}")
    public ResponseEntity<Object> httpDelete(
            @MatrixVariable(pathVar = "matrixVars") MultiValueMap<String, String> matrixVars) {
        return httpDelete0(matrixVars);
    }
}


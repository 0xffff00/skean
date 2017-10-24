package party.threebody.skean.web.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * a simple RestController supporting typical CRUD operations of an entity
 * bean<br>
 * ,which contains a single-column primary key.
 *
 * @param <E> type of the entity bean
 * @author hzk
 * @since skean 2.1
 */
public abstract class MultiPKsMatrixVarCrudRestController<E> extends MatrixVarCrudRestController<E> {

    @Override
    public void buildCrudFunctions(CrudFunctions.Builder<E> builder) {
        // NO-OP
    }

    public abstract void buildCrudFunctions(MultiPKsCrudFunctions.Builder<E, List<String>> builder);

    protected MultiPKsCrudFunctions<E> getCrudFunctions() {
        return (MultiPKsCrudFunctions<E>) super.getCrudFunctions();
    }

    /**
     * @param entity
     * @return
     */
    @PostMapping("")
    public ResponseEntity<E> httpCreate(@RequestBody E entity) {
        E created = getCrudFunctions().getOneCreatorWithReturn().apply(entity);
        List<Object> pk = getCrudFunctions().getPkGetter().apply(entity);
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/{pk}").buildAndExpand(pk).toUri();
        return ResponseEntity.created(location).body(created);

    }

}


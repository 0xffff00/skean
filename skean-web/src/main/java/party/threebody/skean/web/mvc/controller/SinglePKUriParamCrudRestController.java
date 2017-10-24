package party.threebody.skean.web.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import party.threebody.skean.web.mvc.MultiValueMaps;

import java.net.URI;
import java.util.Map;

/**
 * a simple RestController supporting typical CRUD operations of an entity
 * bean<br>
 * ,which contains a single-column primary key.
 *
 * @param <E>  type of the entity bean
 * @param <PK> primitive type of the single-column primary key
 * @author hzk
 * @since skean 2.1
 */
public abstract class SinglePKUriParamCrudRestController<E, PK> extends UriParamCrudRestController<E> {

    @Override
    public void buildCrudFunctions(CrudFunctions.Builder<E> builder) {
        //NO-OP
    }

    public abstract void buildCrudFunctions(SinglePKCrudFunctions.Builder<E, PK> builder);

    protected SinglePKCrudFunctions<E, PK> getCrudFunctions() {
        SinglePKCrudFunctions.Builder<E, PK> builder = new SinglePKCrudFunctions.Builder<>();
        buildCrudFunctions(builder);
        return builder.build();
    }

    @GetMapping("/{pk}")
    public ResponseEntity<E> httpReadOne(@PathVariable PK pk) {
        E entity = getCrudFunctions().getOneReader().apply(pk);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping("")
    public ResponseEntity<E> httpCreate(@RequestBody E entity) {
        E created = getCrudFunctions().getOneCreatorWithReturn().apply(entity);
        PK pk = getCrudFunctions().getPkGetter().apply(entity);
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/{pk}").buildAndExpand(pk).toUri();
        return ResponseEntity.created(location).body(created);

    }

    @PutMapping("/{pk}")
    public ResponseEntity<Object> httpCreateOrUpdate(@PathVariable PK pk, @RequestBody E entity) {
        E original = getCrudFunctions().getOneReader().apply(pk);
        if (original == null) {    //create if not exists
            E created = getCrudFunctions().getOneCreatorWithReturn().apply(entity);
            URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                    .path("/{pk}").buildAndExpand(pk).toUri();
            return ResponseEntity.created(location).build();
        } else {  //update if exists
            Integer rna = getCrudFunctions().getOneUpdater().apply(entity, pk);
            return CrudRestControllerUtils.respondRowNumAffected(rna);
        }

    }

    @PatchMapping("/{pk}")
    public ResponseEntity<Object> httpPartialUpdate(@PathVariable PK pk,
                                                    @RequestParam MultiValueMap<String, String> reqestParamMap) {
        Map<String, Object> varMap = MultiValueMaps.toMap(reqestParamMap);
        Integer rna = getCrudFunctions().getOnePartialUpdater().apply(varMap, pk);
        return CrudRestControllerUtils.respondRowNumAffected(rna);
    }

    @DeleteMapping("/{pk}")
    public ResponseEntity<Object> httpDelete(@PathVariable PK pk) {
        Integer rna = getCrudFunctions().getOneDeleter().apply(pk);
        return CrudRestControllerUtils.respondRowNumAffected(rna);
    }

}


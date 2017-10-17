package party.threebody.skean.web.testapp.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import party.threebody.skean.lang.ObjectMappers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * a simple RestController supporting typical CRUD operations of an entity
 * bean<br>
 * ,which contains a single-column primary key.
 *
 * @param <E>  type of the entity bean
 * @param <PK> primitive type of the single-column primary key
 * @author hzk
 * @since 2017-10-14
 */
public abstract class SinglePKCrudRestController<E, PK> {

    public abstract void buildCrudFunctions(CrudFunctionsBuilder<E, PK> builder);

    protected SimpleCrudFunctions<E, PK> getCrudFunctions(){
        CrudFunctionsBuilder<E, PK> builder=new CrudFunctionsBuilder<>();
        buildCrudFunctions(builder);
        return builder.build();
    }

    @GetMapping("/{pk}")
    public ResponseEntity<E> httpReadOne(@PathVariable PK pk) {
        E entity = getCrudFunctions().getOneReader().apply(pk);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping("")
    public ResponseEntity<List<E>> httpReadList(@RequestParam Map<String, String> reqestParamMap) {
        return ControllerUtils.respondListAndCountByPLOx(reqestParamMap,
                getCrudFunctions().getListReader(), getCrudFunctions().getCountReader());
    }

    @PostMapping("")
    public ResponseEntity<E> httpCreate(@RequestBody E entity) {
        E created = getCrudFunctions().getCreator().apply(entity);
        PK pk = getCrudFunctions().getPkGetter().apply(entity);
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/{pk}").buildAndExpand(pk).toUri();
        return ResponseEntity.created(location).body(created);

    }

    @PutMapping("/{pk}")
    public ResponseEntity<Object> httpCreateOrUpdate(@PathVariable PK pk, @RequestBody E entity) {
        E original = getCrudFunctions().getOneReader().apply(pk);
        if (original == null) {    //create if not exists
            E created = getCrudFunctions().getCreator().apply(entity);
            URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                    .path("/{pk}").buildAndExpand(pk).toUri();
            return ResponseEntity.created(location).build();
        } else {  //update if exists
            Integer rna = getCrudFunctions().getEntireUpdater().apply(entity, pk);
            return ControllerUtils.respondRowNumAffected(rna);
        }

    }

    @PatchMapping("/{pk}")
    public ResponseEntity<Object> httpPartialUpdate(@PathVariable PK pk,
                                                    @RequestBody Map<String, Object> reqestParamMap) {
        Integer rna = getCrudFunctions().getPartialUpdater().apply(reqestParamMap, pk);
        return ControllerUtils.respondRowNumAffected(rna);
    }

    @DeleteMapping("/{pk}")
    public ResponseEntity<Object> httpDelete(@PathVariable PK pk) {
        Integer rna = getCrudFunctions().getDeleter().apply(pk);
        return ControllerUtils.respondRowNumAffected(rna);
    }
}


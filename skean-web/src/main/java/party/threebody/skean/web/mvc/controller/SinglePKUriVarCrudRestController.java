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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
public abstract class SinglePKUriVarCrudRestController<E, PK> extends UriVarCrudRestController<E> {

    @Override
    public void buildCrudFunctions(CrudFunctionsBuilder<E> builder) {
        //NO-OP
    }

    public abstract void buildCrudFunctions(SinglePKCrudFunctionsBuilder<E, PK> builder);

    @Override
    protected SinglePKCrudFunctions<E, PK> getCrudFunctions() {
        SinglePKCrudFunctionsBuilder<E, PK> builder = new SinglePKCrudFunctionsBuilder<>();
        buildCrudFunctions(builder);
        return builder.build();
    }

    @GetMapping("/{pk}")
    public ResponseEntity<E> httpReadOne(@PathVariable PK pk) {
        E entity = getCrudFunctions().getOneReader().apply(pk);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
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
            return respondRowNumAffected(rna);
        }

    }

    @PatchMapping("/{pk}")
    public ResponseEntity<Object> httpPartialUpdate(@PathVariable PK pk,
                                                    @RequestBody Map<String, Object> reqestParamMap) {
        Integer rna = getCrudFunctions().getOnePartialUpdater().apply(reqestParamMap, pk);
        return respondRowNumAffected(rna);
    }

    @DeleteMapping("/{pk}")
    public ResponseEntity<Object> httpDelete(@PathVariable PK pk) {
        Integer rna = getCrudFunctions().getOneDeleter().apply(pk);
        return respondRowNumAffected(rna);
    }

}


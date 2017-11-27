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

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
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
    public void buildCrudFunctions(CrudFunctionsBuilder<E> builder) {
        // NO-OP
    }

    public abstract void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<E> builder);

    @Override
    protected MultiPKsCrudFunctions<E> getCrudFunctions() {
        MultiPKsCrudFunctionsBuilder<E>  builder = new MultiPKsCrudFunctionsBuilder<>();
        buildCrudFunctions(builder);
        return builder.build();
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


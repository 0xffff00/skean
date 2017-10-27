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


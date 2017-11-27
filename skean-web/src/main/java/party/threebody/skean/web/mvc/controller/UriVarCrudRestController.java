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
 * all parameters are UriVar or PathVar
 *
 * @param <E> type of the entity bean
 * @author hzk
 * @since skean 2.1
 */
public abstract class
UriVarCrudRestController<E> extends CrudRestController<E> {


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


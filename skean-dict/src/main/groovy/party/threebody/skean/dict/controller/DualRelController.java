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

package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.dict.dao.DualRelDao;
import party.threebody.skean.dict.domain.DualRel;
import party.threebody.skean.web.mvc.MultiValueMaps;
import party.threebody.skean.web.mvc.controller.CrudRestControllerUtils;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

import java.util.List;

@RestController
@RequestMapping("/dual-rels")
public class DualRelController extends MultiPKsMatrixVarCrudRestController<DualRel> {

    @Autowired DualRelDao dualRelDao;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<DualRel> builder) {
        builder.fromMultiPKsCrudDAO(dualRelDao);
    }

    @GetMapping("2/{matrixVars}")
    public Object httpReadList22(
            @MatrixVariable MultiValueMap<String, String> matrixVars) {
       return MultiValueMaps.toMap(matrixVars);
    }
}

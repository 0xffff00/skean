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

package party.threebody.skean.id.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.id.dao.RoleDAO;
import party.threebody.skean.id.model.Role;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;

@RequestMapping("/id/roles")
@RestController
public class RoleController extends SinglePKUriVarCrudRestController<Role, String> {

    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Role, String> builder) {
        builder.fromSinglePKCrudDAO(roleDAO);

    }

    @Autowired
    RoleDAO roleDAO;


}

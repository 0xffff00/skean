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

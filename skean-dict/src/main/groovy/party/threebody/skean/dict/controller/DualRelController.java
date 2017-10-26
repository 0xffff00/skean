package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.dao.DualRelDao;
import party.threebody.skean.dict.domain.DualRel;
import party.threebody.skean.web.mvc.controller.CrudRestControllerUtils;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

@RestController
@RequestMapping("/dual-rels")
public class DualRelController extends MultiPKsMatrixVarCrudRestController<DualRel> {

    @Autowired DualRelDao dualRelDao;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<DualRel> builder) {
        builder.fromMultiPKsCrudDAO(dualRelDao);
    }


}

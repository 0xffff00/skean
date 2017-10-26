package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.dao.Ge1RelDao;
import party.threebody.skean.dict.domain.Ge1Rel;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.web.mvc.controller.CrudRestControllerUtils;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

@RestController
@RequestMapping("/ge1-rels")
public class Ge1RelController extends MultiPKsMatrixVarCrudRestController<Ge1Rel> {

    @Autowired Ge1RelDao ge1RelDao;
    @Autowired WordService wordService;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<Ge1Rel> builder) {
        builder.fromMultiPKsCrudDAO(ge1RelDao);
    }

}

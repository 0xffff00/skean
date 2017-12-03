package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.X1Relation;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

@RestController
@RequestMapping("/relations/x1/")
public class X1RelationController extends MultiPKsMatrixVarCrudRestController<X1Relation> {

    @Autowired X1RelationDao x1RelationDao;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<X1Relation> builder) {
        builder.fromMultiPKsCrudDAO(x1RelationDao);
    }
}

package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.dao.BasicRelationDao;
import party.threebody.skean.dict.domain.BasicRelation;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

@RestController
@RequestMapping("/relations/basic/")
public class BasicRelationController extends MultiPKsMatrixVarCrudRestController<BasicRelation> {

    @Autowired BasicRelationDao basicRelationDao;
    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<BasicRelation> builder) {
        builder.fromMultiPKsCrudDAO(basicRelationDao);
    }
}

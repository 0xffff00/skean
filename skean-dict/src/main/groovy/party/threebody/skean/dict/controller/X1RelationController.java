package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.X1Relation;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

@RestController
@RequestMapping("/relations/x1")
public class X1RelationController extends MultiPKsMatrixVarCrudRestController<X1Relation> {
    @Autowired X1RelationDao x1RelationDao;

    @Autowired WordService wordService;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<X1Relation> builder) {
        builder.fromMultiPKsCrudDAO(x1RelationDao)
                .oneCreator(wordService::createX1Relation);
    }

    @PostMapping(value = "", params = {"batch"})
    public ResponseEntity<X1Relation> httpCreate(@RequestBody X1Relation entity, @RequestParam boolean batch) {
        if (batch) {
            return respondRowNumAffected(wordService.createX1Relations(entity, "\\s+"));
        }
        return super.httpCreate(entity);

    }
}

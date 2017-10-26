package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.dao.Ge2RelDao;
import party.threebody.skean.dict.domain.Ge2Rel;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.web.mvc.controller.CrudRestControllerUtils;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

@RestController
@RequestMapping("/ge2-rels")
public class Ge2RelController extends MultiPKsMatrixVarCrudRestController<Ge2Rel> {

    @Autowired Ge2RelDao ge2RelDao;
    @Autowired WordService wordService;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<Ge2Rel> builder) {
        builder.fromMultiPKsCrudDAO(ge2RelDao);
    }

    @DeleteMapping("/{rel}")
    public ResponseEntity<?> delete2(@MatrixVariable String key, @MatrixVariable String attr) {
        int rna = wordService.deleteGe2Rels(key, attr);
        return CrudRestControllerUtils.respondRowNumAffected(rna);
    }
}

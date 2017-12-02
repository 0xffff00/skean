package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.domain.AliasRel;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

import java.util.Arrays;

@RestController
@RequestMapping("alias-rels")
public class AliasRelController extends MultiPKsMatrixVarCrudRestController<AliasRel> {
    @Autowired WordService wordService;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<AliasRel> builder) {
        builder.pkGetter(ar -> Arrays.asList(ar.getKey(), ar.getVal()))
                .oneReader(pks -> wordService.getAliasRelByKV((String) pks.get(0), (String) pks.get(1)))
                .oneCreator(wordService::createAliasRel)
                .oneDeleter(pks -> wordService.deleteAliasRelsByKV((String) pks.get(0), (String) pks.get(1)));
    }
}

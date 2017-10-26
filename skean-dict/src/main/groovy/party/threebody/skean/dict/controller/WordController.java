package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.domain.Word;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctions;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController extends SinglePKUriVarCrudRestController<Word,String> {
    @Autowired WordDao wordDao;
    @Autowired WordService wordService;
    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Word,String> builder) {
        builder.fromSinglePKCrudDAO(wordDao);
    }


    @GetMapping("/temporaryTexts")
    public List<String> listTemporaryTexts() {
        return wordService.listTemporaryTexts();
    }
}

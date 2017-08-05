package party.threebody.skean.dict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.dict.model.Word;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.mvc.generic.AbstractCrudRestController;

@RestController
@RequestMapping("/dict/words")
class WordController extends AbstractCrudRestController<Word,String>{
	@Autowired WordService wordService;

	@Override
	protected Word create(Word entity) {
		return wordService.createWord(entity);
	}

	@Override
	protected List<Word> readList(QueryParamsSuite qps) {
		return wordService.listWords(qps);
	}

	@Override
	protected int readCount(QueryParamsSuite qps) {
		return wordService.countWords(qps);
	}

	@Override
	protected Word readOne(String pk) {
		return wordService.getWordWithRels(pk);
	}

	@Override
	protected int update(Word entity, String pk) {
		return wordService.updateWord(entity, pk);
	}

	@Override
	protected int delete(String pk) {
		return wordService.deleteWord(pk);
	}
	
	
	

	

	



	
	



	
	
}

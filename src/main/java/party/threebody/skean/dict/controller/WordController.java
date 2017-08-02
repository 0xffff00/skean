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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Word> readList(QueryParamsSuite qps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int readCount(QueryParamsSuite qps) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Word readOne(String pk) {
		return wordService.getWord(pk);
	}

	@Override
	protected int update(Word entity, String pk) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int delete(String pk) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

	

	



	
	



	
	
}

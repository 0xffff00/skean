package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.skean.dict.model.Word;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.mvc.generic.SimpleCrudRestConfig;
import party.threebody.skean.mvc.generic.SimpleCrudRestController;

@RestController
@RequestMapping("/dict/words")
class WordController extends SimpleCrudRestController<Word,String>{
	@Autowired WordService wordService;
	
	
	protected void set1() {
		setConfig(new SimpleCrudRestConfig<Word, String>(wordService::getWord, null, null, null, null, null));
	}

	

	



	
	



	
	
}

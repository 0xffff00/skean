package party.threebody.skean.dict.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import party.threebody.skean.dict.model.Word
import party.threebody.skean.dict.service.WordService
import party.threebody.skean.mvc.generic.SimpleCrudRestController
import party.threebody.skean.mvc.generic.GenericCrudService


@RestController
@RequestMapping("/dict/words")
class WordController extends SimpleCrudRestController<Word,String>{
	@Autowired WordService wordService
	
	@Override
	public GenericCrudService<Word, String> getCrudService() {
		wordService
	}

	
	@GetMapping('/{text}')
	public Object treeParts(@PathVariable String text){
		def w=wordService.getWord(text)
		w.setDefinitionTrees(null)
		w
	}



	
	



	
	
}

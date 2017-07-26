package party.threebody.skean.dict.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import party.threebody.s4g.dict.Noun
import party.threebody.skean.dict.service.WordService
import party.threebody.skean.mvc.generic.GenericMapCrudRestControllerTemplate


@RestController
@RequestMapping("/dict/words")
class WordController {
	@Autowired GenericMapCrudRestControllerTemplate controllerTemplate
	@Autowired WordService wordService
	

	
	@GetMapping('/{text}')
	public Object treeParts(@PathVariable String text){
		def w=wordService.getWord(text)
		w.setDefinitionTrees(null)
		w
	}


	
	
}

package party.threebody.s4g.dict

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import party.threebody.skean.mvc.generic.GenericMapCrudRestControllerTemplate


@RestController
@RequestMapping("/dict")
class DictController {
	@Autowired GenericMapCrudRestControllerTemplate controllerTemplate;
	@Autowired DictService1 dictService;
	final table='dct_noun'
	final String[] byCols=['word', 'qual']
	final String[] afCols=['word', 'qual', 'lang', 'type']
	@GetMapping("/nouns")
	public ResponseEntity listNouns(@RequestParam Map reqestParamMap){
		controllerTemplate.list(table,reqestParamMap )
	}
	
	@GetMapping('/nouns/allInRel')
	public Object allInRel(){
		dictService.listAllInRel()
	}
	
	@GetMapping('/nouns/{word}/treeParts')
	public Object treeParts(@PathVariable String word){
		dictService.treeParts(word)
	}

	@PostMapping("/nouns")
	public ResponseEntity createNoun(@RequestBody Map reqestParamMap){
		controllerTemplate.createAndGet(table, afCols, reqestParamMap, byCols)
	}

	@DeleteMapping("/nouns/{word}({qual})")
	public ResponseEntity deleteNoun(@PathVariable String word,@PathVariable String qual){
		controllerTemplate.delete(table, byCols, [word, qual] as String[])
	}
	@PutMapping("/nouns/{word}({qual})")
	public ResponseEntity updateNoun(@RequestBody Map reqestParamMap,@PathVariable String word, @PathVariable String qual){
		//TODO distinguish path var & post var
		controllerTemplate.update(table, afCols, reqestParamMap, byCols,[word, qual] as String[])
	}

	@GetMapping('/nouns/{word:[^()]+}')
	public Noun getNoun(@PathVariable String word){
		dictService.getNoun(word)
	}
	@GetMapping('/nouns/{word}({qual})')
	public Noun getNoun(@PathVariable String word,@PathVariable String qual){
		controllerTemplate.get(table, byCols, [word, qual] as String[])
	}
	
	@GetMapping('/nouns/{word}/related')
	public Object getRelated(@PathVariable String word){
		[
			aliasObjects:dictService.listRelatedAliasObject(word),
			instanceObjects:dictService.listRelatedInstanceObject(word),
			subjects:dictService.listRelatedSubject(word)
		]
	}
	
	
}

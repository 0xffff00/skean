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
	@Autowired DictService dictService;
	final table='dct_noun'
	final String[] byCols=['word', 'qual']
	final String[] afCols=['word', 'qual', 'lang', 'type']
	@GetMapping("/nouns")
	public ResponseEntity listNouns(@RequestParam Map reqestParamMap){
		controllerTemplate.list(table,reqestParamMap )
	}

	@PostMapping("/nouns")
	public ResponseEntity createNoun(@RequestBody Map reqestParamMap){
		controllerTemplate.createAndGet(table, afCols, reqestParamMap, byCols)
	}



	@DeleteMapping("/nouns/{word}")
	public ResponseEntity deleteNoun(@PathVariable String word){
		controllerTemplate.delete(table, byCols, [word,''] as String[])
	}
	@PutMapping("/nouns/{word}")
	public ResponseEntity updateNoun(@RequestBody Map reqestParamMap,@PathVariable String word ){
		//TODO distinguish path var & post var
		controllerTemplate.update(table, afCols, reqestParamMap, byCols,[word,''] as String[])
	}
	@PatchMapping("/nouns/{word}")
	public ResponseEntity patchNoun(@RequestBody Map reqestParamMap,@PathVariable String word ){
		controllerTemplate.update(table, reqestParamMap, [word:word,qual:''])
	}
	@GetMapping('/nouns/{word:[^()]+}')
	public Noun getNoun(@PathVariable String word){
		dictService.getNoun(word)
	}

	@GetMapping('/nouns/x2/{word}')
	public Noun getNoun2(@PathVariable String word){
		dictService.getNoun2(word)
	}

	@GetMapping('/nouns/x3/{word}({qual})')
	public Noun getNoun3(String word,String qual){
		//controllerTemplate.get(table, [word:word,qual:qual])
		dictService.getNoun(word, qual)
	}
}

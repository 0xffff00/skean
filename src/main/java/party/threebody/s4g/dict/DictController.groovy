package party.threebody.s4g.dict

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import party.threebody.skean.mvc.generic.GenericMapCrudRestControllerTemplate


@RestController
@RequestMapping("/dict")
class DictController {
	@Autowired GenericMapCrudRestControllerTemplate controllerTemplate;
	final tableName='dct_noun'
	final pkCols=['word', 'qual']
	@GetMapping("/nouns")
	public ResponseEntity listNouns(@RequestParam Map reqestParamMap){
		controllerTemplate.list(reqestParamMap, tableName)
	}

	public ResponseEntity createNoun(@RequestParam Map reqestParamMap ){
		controllerTemplate.createAndGet(tableName, reqestParamMap, pkCols)
	}

	public ResponseEntity deleteNoun(@RequestParam Map reqestParamMap ){
		controllerTemplate.delete(tableName, reqestParamMap, pkCols)
	}

	public ResponseEntity updateNoun(@RequestParam Map reqestParamMap ){
		controllerTemplate.update(tableName, reqestParamMap, pkCols)
	}

	@RequestMapping('/nouns/{word}')
	public ResponseEntity getNoun(String word){
		getNoun(word,null)
	}

	@RequestMapping('/nouns/{word}({qual})')
	public ResponseEntity getNoun(String word,String qual){
		controllerTemplate.get(tableName, [word:word,qual:qual])
	}
}

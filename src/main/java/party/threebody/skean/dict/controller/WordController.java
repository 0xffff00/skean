package party.threebody.skean.dict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.dict.model.DualRel;
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

	/**
	 * TODO refactor to matrix parameter: ';cate=temporaryTexts'
	 * @return
	 */
	@GetMapping("/temporaryTexts")
	public List<String> listTemporaryTexts() {
		return wordService.listTemporaryTexts();
	}
	
	
	@PostMapping("any/dualRels")
	public ResponseEntity<DualRel> createDualRel(@RequestBody DualRel dualRel){	
		DualRel created=wordService.createDualRel(dualRel);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);		
	}
	
	@DeleteMapping("any/dualRels/{key}&{attr}&{vno}&{val}")
	public ResponseEntity<Object> deleteDualRel(@PathVariable String key,@PathVariable String attr,@PathVariable Integer vno,@PathVariable String val){
		int rna=wordService.deleteDualRel(key, attr, vno, val);
		return respondRowNumAffected(rna);		
	}
	
	

	

	



	
	



	
	
}

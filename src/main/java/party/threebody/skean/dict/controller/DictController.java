package party.threebody.skean.dict.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.skean.dict.model.DualRel;
import party.threebody.skean.dict.model.Ge1Rel;
import party.threebody.skean.dict.model.Ge2Rel;
import party.threebody.skean.dict.model.Word;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.mvc.generic.ControllerUtils;

@RestController
@RequestMapping("/dict")
public class DictController {
	@Autowired
	WordService wordService;

	@GetMapping("/dual-rels")
	public Collection<DualRel> listDualRels() {
		return wordService.listDualRels();
	}

	@PostMapping("/dual-rels")
	public ResponseEntity<DualRel> createDualRel(@RequestBody DualRel dualRel) {
		DualRel created = wordService.createDualRel(dualRel);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@DeleteMapping("/dual-rels/{rel}")
	public ResponseEntity<Object> deleteDualRel(@MatrixVariable String key, @MatrixVariable String attr,
			@MatrixVariable Integer vno) {
		int rna = wordService.deleteDualRel(key, attr, vno);
		return ControllerUtils.respondRowNumAffected(rna);
	}

	@GetMapping("/ge1-rels")
	public Collection<Ge1Rel> listGe1Rels() {
		return wordService.listGe1Rels();
	}

	@PostMapping("/ge1-rels")
	public ResponseEntity<Ge1Rel> createGe1Rel(@RequestBody Ge1Rel rel) {
		Ge1Rel created = wordService.createGe1Rel(rel);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@DeleteMapping("/ge1-rels/{rel}")
	public ResponseEntity<Object> deleteGe1Rel(@MatrixVariable String key, @MatrixVariable String attr,
			@MatrixVariable(required = false) Integer vno) {
		int rna = 0;
		if (vno == null) {
			rna = wordService.deleteGe1Rels(key, attr);
		}else{
			rna = wordService.deleteGe1Rel(key, attr, vno);
		}
		return ControllerUtils.respondRowNumAffected(rna);
	}

	@GetMapping("/ge2-rels")
	public Collection<Ge2Rel> listGe2Rels() {
		return wordService.listGe2Rels();
	}

	@PostMapping("/ge2-rels")
	public ResponseEntity<Ge2Rel> createGe2Rel(@RequestBody Ge2Rel rel) {
		Ge2Rel created = wordService.createGe2Rel(rel);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@DeleteMapping("/ge2-rels/{rel}")
	public ResponseEntity<Object> deleteGe2Rel(@MatrixVariable String key, @MatrixVariable String attr,
			@MatrixVariable(required = false)  Integer vno) {
		int rna = 0;
		if (vno == null) {
			rna = wordService.deleteGe2Rels(key, attr);
		}else{
			rna = wordService.deleteGe2Rel(key, attr, vno);
		}
		return ControllerUtils.respondRowNumAffected(rna);
	}

	@PostMapping("/words")
	public ResponseEntity<Word> createWord(Word entity) {
		Word created = wordService.createWord(entity);
		return ResponseEntity.created(null).body(created);

	}

	@GetMapping("/words")
	public ResponseEntity<List<Word>> listWords(@RequestParam Map<String, String> reqestParamMap) {
		return ControllerUtils.respondListAndCount(reqestParamMap, wordService::listWords, wordService::countWords);
	}

	@GetMapping("/words/{text}")
	public Word getWord(@PathVariable String text) {
		return wordService.getWordWithRels(text);
	}

	@PutMapping("/words/{text}")
	public ResponseEntity<Object> updateWord(@PathVariable String text, @RequestBody Word entity) {
		return ControllerUtils.respondRowNumAffected(wordService.updateWord(entity, text));
	}

	@DeleteMapping("/words/{text}")
	public ResponseEntity<Object> deleteWord(@PathVariable String text) {
		return ControllerUtils.respondRowNumAffected(wordService.deleteWord(text));
	}

	@GetMapping("/words/temporaryTexts")
	public List<String> listTemporaryTexts() {
		return wordService.listTemporaryTexts();
	}

}

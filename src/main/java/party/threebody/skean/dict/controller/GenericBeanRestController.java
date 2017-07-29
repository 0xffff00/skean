package party.threebody.skean.dict.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import party.threebody.s4g.dict.Noun;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.mvc.generic.GenericMapCrudRestControllerTemplate;
import party.threebody.skean.mvc.generic.GenericMapCrudService;


public class GenericBeanRestController<T> {
	@Autowired
	GenericMapCrudService genericMapCrudService;
	
	
	@GetMapping("/{id}")
	public Noun get(@PathVariable String id){
		return null;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<T>> list(@RequestParam Map<String,Object> reqestParamMap){
		return null;
	}
	
	@PostMapping("/")
	public ResponseEntity<T> create(@RequestParam Map<String,Object> reqestParamMap){
		return null;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable String id){
		return null;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable String id,@RequestParam Map<String,Object> reqestParamMap){
		return null;
	}
	
}

package party.threebody.skean.mvc.generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class RestCrudController<T> {
	
	public ResponseEntity createNoun(T t){
		
		return new ResponseEntity(t, HttpStatus.CREATED);
		
	} 

}

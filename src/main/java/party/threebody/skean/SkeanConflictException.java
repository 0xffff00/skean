package party.threebody.skean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SkeanConflictException extends SkeanException {

	private static final long serialVersionUID = 0xfefee00409L;

	
	public SkeanConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkeanConflictException(String message) {
		super(message);
	}

	
	

}

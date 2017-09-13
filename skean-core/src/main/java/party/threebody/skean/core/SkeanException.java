package party.threebody.skean.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * 
 * @author hzk
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SkeanException extends RuntimeException {

	private static final long serialVersionUID = 0xfefee00400L;

	
	public SkeanException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkeanException(String message) {
		super(message);
	}

}

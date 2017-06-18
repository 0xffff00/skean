package party.threebody.skean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SkeanException extends RuntimeException {

	private static final long serialVersionUID = 0xfefee00400L;

	public SkeanException() {
		super();
	}

	public SkeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SkeanException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkeanException(String message) {
		super(message);
	}

	public SkeanException(Throwable cause) {
		super(cause);
	}
	
	

}

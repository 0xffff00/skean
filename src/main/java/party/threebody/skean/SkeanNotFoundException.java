package party.threebody.skean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SkeanNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 0xfefee00404L;

	public SkeanNotFoundException() {
		super();
	}

	public SkeanNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SkeanNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkeanNotFoundException(String message) {
		super(message);
	}

	public SkeanNotFoundException(Throwable cause) {
		super(cause);
	}
	
	

}

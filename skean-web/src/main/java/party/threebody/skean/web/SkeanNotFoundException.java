package party.threebody.skean.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import party.threebody.skean.misc.SkeanException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SkeanNotFoundException extends SkeanException {

	public SkeanNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkeanNotFoundException(String message) {
		super(message);
	}


}

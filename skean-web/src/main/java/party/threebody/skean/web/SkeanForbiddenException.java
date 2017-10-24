package party.threebody.skean.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import party.threebody.skean.misc.SkeanException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SkeanForbiddenException extends SkeanException {

    public SkeanForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkeanForbiddenException(String message) {
        super(message);
    }


}

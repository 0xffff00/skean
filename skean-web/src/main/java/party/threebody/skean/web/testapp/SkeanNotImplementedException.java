package party.threebody.skean.web.testapp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import party.threebody.skean.misc.SkeanException;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class SkeanNotImplementedException extends SkeanException {

    public SkeanNotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkeanNotImplementedException(String message) {
        super(message);
    }

    public SkeanNotImplementedException() {
        super("Not Implemented Yet.");
    }
}

package party.threebody.skean.web.testapp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import party.threebody.skean.misc.SkeanException;

@ResponseStatus(HttpStatus.CONFLICT)
public class SkeanConflictException extends SkeanException {

    public SkeanConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkeanConflictException(String message) {
        super(message);
    }


}

package party.threebody.skean.data.query;

import party.threebody.skean.misc.SkeanException;

public class SkeanDataException  extends SkeanException{
    public SkeanDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkeanDataException(String message) {
        super(message);
    }
}

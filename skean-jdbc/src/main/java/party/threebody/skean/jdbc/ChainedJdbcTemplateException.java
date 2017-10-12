package party.threebody.skean.jdbc;


import party.threebody.skean.misc.SkeanException;

public class ChainedJdbcTemplateException extends SkeanException {

    private static final long serialVersionUID = 0x2a398;

    public ChainedJdbcTemplateException(String message) {
        super(message);
    }

    public ChainedJdbcTemplateException(String message, Throwable cause) {
        super(message, cause);
    }


}
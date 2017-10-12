package party.threebody.skean.jdbc;

import party.threebody.skean.misc.SkeanException;

@SuppressWarnings("serial")
public class SkeanSqlSecurityException extends SkeanException {
    public SkeanSqlSecurityException(String message) {
        super(message);
    }
}
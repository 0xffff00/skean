package party.threebody.herd.util;

public class HerdException extends RuntimeException {

    public HerdException() {
        super();
    }

    public HerdException(String message) {
        super(message);
    }

    public HerdException(String message, Throwable cause) {
        super(message, cause);
    }
}

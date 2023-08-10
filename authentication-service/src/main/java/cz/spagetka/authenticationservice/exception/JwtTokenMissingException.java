package cz.spagetka.authenticationservice.exception;

public class JwtTokenMissingException extends RuntimeException {
    public JwtTokenMissingException() {
        super();
    }

    public JwtTokenMissingException(String message) {
        super(message);
    }

    public JwtTokenMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenMissingException(Throwable cause) {
        super(cause);
    }

    protected JwtTokenMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

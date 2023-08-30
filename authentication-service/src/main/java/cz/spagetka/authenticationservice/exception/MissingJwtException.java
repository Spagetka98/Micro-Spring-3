package cz.spagetka.authenticationservice.exception;

public class MissingJwtException extends RuntimeException {
    public MissingJwtException() {
    }

    public MissingJwtException(String message) {
        super(message);
    }

    public MissingJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingJwtException(Throwable cause) {
        super(cause);
    }

    public MissingJwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

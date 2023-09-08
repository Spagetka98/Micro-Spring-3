package cz.spagetka.authenticationservice.exception.passwordToken;

public class MissingPasswordTokenException extends RuntimeException {
    public MissingPasswordTokenException(String format) {
    }

    public MissingPasswordTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingPasswordTokenException(Throwable cause) {
        super(cause);
    }

    public MissingPasswordTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MissingPasswordTokenException() {
    }
}

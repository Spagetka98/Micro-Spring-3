package cz.spagetka.authenticationservice.exception;

public class RefreshTokenExpirationException extends RuntimeException {

    public RefreshTokenExpirationException() {
    }

    public RefreshTokenExpirationException(String message) {
        super(message);
    }

    public RefreshTokenExpirationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenExpirationException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenExpirationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

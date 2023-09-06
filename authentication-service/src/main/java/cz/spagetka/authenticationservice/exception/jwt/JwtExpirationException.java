package cz.spagetka.authenticationservice.exception.jwt;

public class JwtExpirationException extends RuntimeException {

    public JwtExpirationException() {
    }

    public JwtExpirationException(String message) {
        super(message);
    }

    public JwtExpirationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtExpirationException(Throwable cause) {
        super(cause);
    }

    public JwtExpirationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package cz.spagetka.authenticationservice.exception.passwordToken;

public class PasswordTokenExpirationException extends RuntimeException {
    public PasswordTokenExpirationException(String format) {
    }

    public PasswordTokenExpirationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordTokenExpirationException(Throwable cause) {
        super(cause);
    }

    public PasswordTokenExpirationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PasswordTokenExpirationException() {
    }
}

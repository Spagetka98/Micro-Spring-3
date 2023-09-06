package cz.spagetka.authenticationservice.exception.verificationToken;

public class VerificationTokenExpirationException extends RuntimeException {
    public VerificationTokenExpirationException(String format) {
    }

    public VerificationTokenExpirationException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationTokenExpirationException(Throwable cause) {
        super(cause);
    }

    public VerificationTokenExpirationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public VerificationTokenExpirationException() {
    }
}

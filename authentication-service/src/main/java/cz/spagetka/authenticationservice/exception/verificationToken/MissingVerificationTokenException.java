package cz.spagetka.authenticationservice.exception.verificationToken;

public class MissingVerificationTokenException extends RuntimeException {
    public MissingVerificationTokenException() {
    }

    public MissingVerificationTokenException(String message) {
        super(message);
    }

    public MissingVerificationTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingVerificationTokenException(Throwable cause) {
        super(cause);
    }

    public MissingVerificationTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

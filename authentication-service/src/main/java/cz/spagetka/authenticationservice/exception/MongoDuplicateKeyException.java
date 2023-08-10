package cz.spagetka.authenticationservice.exception;

public class MongoDuplicateKeyException extends RuntimeException {
    public MongoDuplicateKeyException() {
    }

    public MongoDuplicateKeyException(String message) {
        super(message);
    }

    public MongoDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoDuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public MongoDuplicateKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

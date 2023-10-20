package cz.spagetka.newsService.exception;

public class IllegalRequestInputException extends RuntimeException {
    public IllegalRequestInputException() {
        super();
    }

    public IllegalRequestInputException(String message) {
        super(message);
    }

    public IllegalRequestInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRequestInputException(Throwable cause) {
        super(cause);
    }

    protected IllegalRequestInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

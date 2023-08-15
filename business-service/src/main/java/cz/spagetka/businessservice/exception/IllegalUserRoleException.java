package cz.spagetka.businessservice.exception;

public class IllegalUserRoleException extends RuntimeException {

    public IllegalUserRoleException() {
        super();
    }

    public IllegalUserRoleException(String message) {
        super(message);
    }

    public IllegalUserRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalUserRoleException(Throwable cause) {
        super(cause);
    }

    protected IllegalUserRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

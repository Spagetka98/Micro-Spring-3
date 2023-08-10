package cz.spagetka.authenticationservice.exception;

public class UserInformationTaken extends RuntimeException {

    public UserInformationTaken() {
    }

    public UserInformationTaken(String message) {
        super(message);
    }

    public UserInformationTaken(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInformationTaken(Throwable cause) {
        super(cause);
    }

    public UserInformationTaken(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

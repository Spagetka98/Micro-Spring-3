package cz.spagetka.authenticationservice.exceptionHandler;

public record ErrorResponse(int statusCode, String errorMsg, String creationTime) {
}

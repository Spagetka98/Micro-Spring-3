package cz.spagetka.businessservice.exception.exceptionHandler;

public record ErrorResponse(int statusCode, String errorMsg, String creationTime) {
}

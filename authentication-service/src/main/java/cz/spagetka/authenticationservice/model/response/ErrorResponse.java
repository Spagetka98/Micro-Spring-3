package cz.spagetka.authenticationservice.model.response;

public record ErrorResponse(int statusCode, String errorMsg, String creationTime) {
}

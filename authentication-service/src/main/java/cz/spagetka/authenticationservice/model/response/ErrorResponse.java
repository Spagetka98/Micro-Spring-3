package cz.spagetka.authenticationservice.model.response;

public record ErrorResponse(int statusCode, String message, String timestamp) {
}

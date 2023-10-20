package cz.spagetka.newsService.model.response;

public record ErrorResponse(int statusCode, String errorMsg, String creationTime) {
}

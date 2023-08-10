package cz.spagetka.apigateway.model.responses;

public record AuthExceptionResponse(int errorCode,String errorMsg,String timeStamp) {
}

package cz.spagetka.newsService.exceptionHandler;

import cz.spagetka.newsService.exception.CommentNotFoundException;
import cz.spagetka.newsService.exception.NewsNotFoundException;
import cz.spagetka.newsService.exception.UserNotFoundException;
import cz.spagetka.newsService.model.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, NewsNotFoundException.class, UserNotFoundException.class, CommentNotFoundException.class,
            MissingServletRequestPartException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(Exception exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), Instant.now().toString());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),"File is too large!",Instant.now().toString());
    }

    @ExceptionHandler({IllegalStateException.class,IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerErrorException(Exception exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), Instant.now().toString());
    }

}

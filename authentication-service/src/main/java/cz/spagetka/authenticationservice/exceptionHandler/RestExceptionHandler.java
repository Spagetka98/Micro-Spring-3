package cz.spagetka.authenticationservice.exceptionHandler;

import cz.spagetka.authenticationservice.exception.jwt.InvalidJwtException;
import cz.spagetka.authenticationservice.exception.jwt.JwtExpirationException;
import cz.spagetka.authenticationservice.exception.jwt.MissingJwtException;
import cz.spagetka.authenticationservice.exception.mongo.MongoDuplicateKeyException;
import cz.spagetka.authenticationservice.exception.passwordToken.MissingPasswordTokenException;
import cz.spagetka.authenticationservice.exception.passwordToken.PasswordTokenExpirationException;
import cz.spagetka.authenticationservice.exception.refreshToken.InvalidRefreshTokenException;
import cz.spagetka.authenticationservice.exception.refreshToken.RefreshTokenExpirationException;
import cz.spagetka.authenticationservice.exception.user.IncorrectPasswordException;
import cz.spagetka.authenticationservice.exception.user.UserInformationTaken;
import cz.spagetka.authenticationservice.exception.user.UserNotFoundException;
import cz.spagetka.authenticationservice.exception.verificationToken.MissingVerificationTokenException;
import cz.spagetka.authenticationservice.exception.verificationToken.VerificationTokenExpirationException;
import cz.spagetka.authenticationservice.model.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, RefreshTokenExpirationException.class, MissingVerificationTokenException.class,
            MissingPasswordTokenException.class, IncorrectPasswordException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(Exception exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), Instant.now().toString());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors.toString(), Instant.now().toString());
    }

    @ExceptionHandler({DisabledException.class, VerificationTokenExpirationException.class, PasswordTokenExpirationException.class})
    @ResponseStatus(value = HttpStatus.LOCKED)
    public ErrorResponse disabledException(Exception exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.LOCKED.value(), exception.getMessage(), Instant.now().toString());
    }

    @ExceptionHandler({UserInformationTaken.class, MongoDuplicateKeyException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse conflictException(Exception exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage(), Instant.now().toString());
    }

    @ExceptionHandler({InvalidJwtException.class, InvalidRefreshTokenException.class, UserNotFoundException.class, IllegalStateException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse InvalidJwtExpirationException(Exception exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), Instant.now().toString());
    }

    @ExceptionHandler({MissingJwtException.class, JwtExpirationException.class, BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorizedException(Exception exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), Instant.now().toString());
    }
}

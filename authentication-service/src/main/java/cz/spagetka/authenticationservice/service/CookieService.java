package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CookieService {
    ResponseCookie getJwtCookie(@NotBlank(message = "Parameter jwt cannot be null or empty!") String jwt);
    ResponseCookie getJwRefreshTokenCookie(@NotNull(message = "Parameter refreshToken cannot be null!") RefreshToken refreshToken);

    ResponseCookie cleanJwtCookie();
    ResponseCookie cleanRefreshTokenCookie();
}

package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RefreshTokenService {
    RefreshToken generateRefreshToken();

    boolean isTokenExpired(
            @NotNull(message = "Parameter refreshToken cannot be null or empty!") RefreshToken refreshToken);
}

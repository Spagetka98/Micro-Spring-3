package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
public interface JwtTokenService {
    String extractUsername(
            @NotBlank(message = "Parameter jwtToken cannot be null or empty!") String jwtToken);

    String generateJwtToken(
            @NotNull(message = "Parameter userDetails cannot be null!") UserDetails userDetails
    );

    String generateJwtToken(
            @NotNull(message = "Parameter extraClaims cannot be null!") Map<String, Object> extraClaims,
            @NotNull(message = "Parameter userDetails cannot be null!") UserDetails userDetails
    );

    boolean isTokenValid(
            @NotBlank(message = "Parameter jwtToken cannot be null or empty!") String jwtToken);

    boolean isTokenExpired(
            @NotBlank(message = "Parameter jwtToken cannot be null or empty!") String jwtToken);

    boolean isJwtTokenAssociatedWithUser(
            @NotBlank(message = "Parameter jwtToken cannot be null or empty!") String jwtToken,
            @NotNull(message = "Parameter possibleTokenOwner cannot be null!") User possibleTokenOwner);
}

package cz.spagetka.authenticationservice.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "token.jwt")
@Validated
public record JwtProperties(
        @NotBlank(message = "JWT property header_name cannot be null or empty !")
        String header_name,
        @NotBlank(message = "JWT property header_starts_with cannot be null or empty !")
        String header_starts_with,
        @NotBlank(message = "JWT property secret_key cannot be null or empty !")
        String secret_key,
        @Min(value = 60,message = "Minimum number of seconds is 60 !")
        long secondsLength
        ) {
}

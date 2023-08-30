package cz.spagetka.authenticationservice.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "token.jwt")
@Validated
public record JwtProperties(
        @NotBlank(message = "JWT property cookie_name cannot be null or empty !")
        String cookie_name,
        @NotBlank(message = "JWT property secret_key cannot be null or empty !")
        String secret_key,
        @Min(value = 60,message = "Minimum number of seconds is 60 !")
        long secondsLength
        ) {
}

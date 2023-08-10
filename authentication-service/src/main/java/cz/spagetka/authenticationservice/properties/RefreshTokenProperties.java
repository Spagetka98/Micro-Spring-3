package cz.spagetka.authenticationservice.properties;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "token.refresh")
@Validated
public record RefreshTokenProperties(
        @Min(value = 1,message = "Minimum number of hours is 1 !")
        long hoursLength
) {
}

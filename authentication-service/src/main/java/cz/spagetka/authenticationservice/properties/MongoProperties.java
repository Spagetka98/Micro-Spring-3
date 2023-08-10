package cz.spagetka.authenticationservice.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "spring.data.mongodb")
@Validated
public record MongoProperties(
        @NotBlank(message = "MongoDB property database name cannot be null or empty !") String name,
        @NotBlank(message = "MongoDB property database uri cannot be null or empty !") String uri
) {
}

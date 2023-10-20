package cz.spagetka.authenticationservice.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "server.servlet")
@Validated
public record ServiceProperties(@NotBlank(message = "Parameter context patch cannot be null or empty!") String contextPath) {
}

package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Request parameter username cannot be null or empty!") String username,
        @NotBlank(message = "Request parameter password cannot be null or empty!") String password) {
}

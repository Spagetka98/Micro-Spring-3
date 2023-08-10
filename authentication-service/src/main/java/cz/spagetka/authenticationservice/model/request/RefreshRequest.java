package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank(message = "Request parameter refreshToken cannot be null or empty!") String refreshToken) {
}

package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.NotBlank;

public record ForgetPasswordRequest(
        @NotBlank(message = "Request parameter email cannot be null or empty!") String email
) {
}

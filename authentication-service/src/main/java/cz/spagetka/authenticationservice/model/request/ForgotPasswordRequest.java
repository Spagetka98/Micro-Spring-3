package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "Email is not valid !")
        @NotBlank(message = "Email cannot be null or empty!")
        String email
) {
}

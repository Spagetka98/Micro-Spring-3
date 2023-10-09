package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.Email;

public record ForgetPasswordRequest(
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "{validation.email.regexp.not_match}")
        String email
) {
}

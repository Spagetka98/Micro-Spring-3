package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "Request parameter passwordToken cannot be null or empty!")
        String passwordToken,
        @Size(min = 8, message = "The password must have more than 8 characters !")
        @Pattern(
                regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\\S+$).+$",
                message = "Password requirements\n" +
                        "        A digit must occur at least once\n" +
                        "        A lower case letter must occur at least once\n" +
                        "        An upper case letter must occur at least once\n" +
                        "        No whitespace allowed in the entire string"
        )
        @NotBlank(message = "The password cannot be null or empty!")
        String newPassword
) {
}

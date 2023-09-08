package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "Request parameter passwordToken cannot be null or empty!") String passwordToken,
        @NotBlank(message = "Request parameter oldPassword cannot be null or empty!") String oldPassword,
        @Size(min = 8, message = "{validation.password.size.too_small}")
        @Pattern(
                regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\\S+$).+$",
                message = "{validation.password.regexp.not_match}"
        ) String newPassword
) {
}

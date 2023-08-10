package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Size.List({
                @Size(min = 3, message = "{validation.username.size.too_small}"),
                @Size(max = 30, message = "{validation.username.size.too_long}")
        })
        @Pattern(
                regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9])+[a-zA-Z0-9]$",
                message = "{validation.username.regexp.not_match}"
        )
        String username,
        @Size(min = 2, message = "{validation.firstName.size.too_small}")
        @Pattern(
                regexp = "^[A-ža-ž]+$",
                message = "{validation.firstName.regexp.not_match}"
        )
        String firstName,
        @Size(min = 2, message = "{validation.lastName.size.too_small}")
        @Pattern(
                regexp = "^[A-ža-ž]+$",
                message = "{validation.lastName.regexp.not_match}"
        )
        String lastName,
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "{validation.email.regexp.not_match}")
        String email,
        @Size(min = 8, message = "{validation.password.size.too_small}")
        @Pattern(
                regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\\S+$).+$",
                message = "{validation.password.regexp.not_match}"
        )
        String password
) {
}

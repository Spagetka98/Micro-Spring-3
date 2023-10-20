package cz.spagetka.authenticationservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

public record RegisterRequest(
        @Size.List({
                @Size(min = 3, message = "The username must have more than 3 characters !"),
                @Size(max = 30, message = "The username must have less than 30 characters !")
        })
        @Pattern(
                regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9])+[a-zA-Z0-9]$",
                message = "Username requirements\n" +
                        "        Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.\n" +
                        "        Username allowed of the dot (.), underscore (_), and hyphen (-).\n" +
                        "        The dot (.), underscore (_), or hyphen (-) must not be the first or last character.\n" +
                        "        The dot (.), underscore (_), or hyphen (-) does not appear consecutively."
        )
        @NotBlank(message = "Username cannot be null or empty!")
        String username,
        @Size(min = 2, message = "The FirstName must have more than 2 characters !")
        @Pattern(
                regexp = "^[A-ža-ž]+$",
                message = "FirstName must have only letters!"
        )
        @NotBlank(message = "FirstName cannot be null or empty!")
        String firstName,
        @Size(min = 2, message = "The LastName must have more than 2 characters !")
        @Pattern(
                regexp = "^[A-ža-ž]+$",
                message = "LastName must have only letters!"
        )
        @NotBlank(message = "LastName cannot be null or empty!")
        String lastName,
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "Email is not valid !")
        @NotBlank(message = "Email cannot be null or empty!")
        String email,
        @Size(min = 8, message = "The password must have more than 8 characters !")
        @Pattern(
                regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\\S+$).+$",
                message = "Password requirements\n" +
                        "        A digit must occur at least once\n" +
                        "        A lower case letter must occur at least once\n" +
                        "        An upper case letter must occur at least once\n" +
                        "        No whitespace allowed in the entire string"
        )
        @NotBlank(message = "Password cannot be null or empty!")
        String password
) {
}

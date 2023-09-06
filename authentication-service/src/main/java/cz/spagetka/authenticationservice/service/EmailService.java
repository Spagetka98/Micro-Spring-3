package cz.spagetka.authenticationservice.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EmailService {
    void sendEmail(
            @NotBlank(message = "Parameter subject cannot be null or empty!") String subject,
            @NotBlank(message = "Parameter to cannot be null or empty!") String to,
            @NotBlank(message = "Parameter text cannot be null or empty!") String text);

    void sendUserVerificationEmail(
            @NotBlank(message = "Parameter userEmail cannot be null or empty!") String userEmail,
            @NotBlank(message = "Parameter userVerificationToken cannot be null or empty!") String userVerificationToken);
}

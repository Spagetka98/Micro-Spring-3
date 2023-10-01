package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.VerificationToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface VerificationTokenService {

    VerificationToken createVerificationToken();

    boolean isVerificationTokenExpired(@NotNull(message = "Parameter verificationToken cannot be null or empty!") VerificationToken verificationToken);
}

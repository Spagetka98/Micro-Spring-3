package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.VerificationToken;
import org.springframework.validation.annotation.Validated;

@Validated
public interface VerificationTokenService {

    VerificationToken createVerificationToken();
}

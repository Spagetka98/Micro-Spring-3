package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.PasswordToken;
import jakarta.validation.constraints.NotNull;

public interface PasswordTokenService {
    PasswordToken createPasswordToken();

    boolean isPasswordTokenExpired(@NotNull(message = "Parameter passwordToken cannot be null or empty!") PasswordToken passwordToken);
}

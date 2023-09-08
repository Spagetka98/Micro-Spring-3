package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.PasswordToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordTokenServiceImpl implements PasswordTokenService{
    @Override
    public PasswordToken createPasswordToken() {
        final int FIFTEEN_MINUTES_EXPIRATION_SECONDS = 15 * 60;
        final String TOKEN_VALUE = UUID.randomUUID().toString();
        final Instant EXPIRATION_DATA = Instant.now().plusSeconds(FIFTEEN_MINUTES_EXPIRATION_SECONDS);

        return PasswordToken.builder()
                .token(TOKEN_VALUE)
                .expirationDate(EXPIRATION_DATA)
                .build();
    }

    @Override
    public boolean isPasswordTokenExpired(PasswordToken passwordToken) {
        return Instant.now().isAfter(passwordToken.getExpirationDate());
    }
}

package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Override
    public VerificationToken createVerificationToken() {
        final int SEVEN_DAYS_EXPIRATION_SECONDS = 7 * 24 * 60 * 60;
        final UUID TOKEN_VALUE = UUID.randomUUID();
        final Instant EXPIRATION_DATA = Instant.now().plusSeconds(SEVEN_DAYS_EXPIRATION_SECONDS);
        final Instant CREATION_DATE = Instant.now();

        return VerificationToken.builder()
                .token(TOKEN_VALUE)
                .expirationDate(EXPIRATION_DATA)
                .creationDate(CREATION_DATE)
                .build();
    }
}

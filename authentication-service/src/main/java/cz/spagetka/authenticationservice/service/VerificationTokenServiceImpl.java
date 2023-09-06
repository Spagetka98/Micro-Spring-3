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
        final String TOKEN_VALUE = UUID.randomUUID().toString();
        final Instant EXPIRATION_DATA = Instant.now().plusSeconds(SEVEN_DAYS_EXPIRATION_SECONDS);

        return VerificationToken.builder()
                .token(TOKEN_VALUE)
                .expirationDate(EXPIRATION_DATA)
                .build();
    }

    @Override
    public boolean isVerificationTokenExpired(VerificationToken verificationToken) {
        return Instant.now().isAfter(verificationToken.getExpirationDate());
    }
}

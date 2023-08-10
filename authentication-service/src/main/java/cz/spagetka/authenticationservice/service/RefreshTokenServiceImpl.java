package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.properties.RefreshTokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenProperties refreshTokenProperties;

    @Override
    public RefreshToken generateRefreshToken() {
        return RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expirationDate(Instant.now().plus(refreshTokenProperties.hoursLength(), ChronoUnit.HOURS))
                .build();
    }

    @Override
    public boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpirationDate().isBefore(Instant.now());
    }
}

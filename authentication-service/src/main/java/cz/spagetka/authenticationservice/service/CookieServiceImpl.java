package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.properties.JwtProperties;
import cz.spagetka.authenticationservice.properties.RefreshTokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {
    private final JwtProperties jwtProperties;
    private final RefreshTokenProperties refreshTokenProperties;

    @Override
    public ResponseCookie getJwtCookie(String jwt) {
        return ResponseCookie
                .from(jwtProperties.cookie_name(), jwt)
                .path("/")
                .maxAge(jwtProperties.secondsLength())
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }

    @Override
    public ResponseCookie getJwRefreshTokenCookie(RefreshToken refreshToken) {
        return ResponseCookie
                .from(refreshTokenProperties.cookie_name(), refreshToken.getToken())
                .path("/")
                .maxAge(refreshToken.getExpirationDate().getEpochSecond())
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }

    @Override
    public ResponseCookie cleanJwtCookie() {
        return ResponseCookie
                .from(jwtProperties.cookie_name(), "")
                .path("/")
                .build();
    }

    @Override
    public ResponseCookie cleanRefreshTokenCookie() {
        return ResponseCookie
                .from(refreshTokenProperties.cookie_name(), "")
                .path("/")
                .build();
    }
}

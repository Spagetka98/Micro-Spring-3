package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.exception.InvalidJwtException;
import cz.spagetka.authenticationservice.properties.JwtProperties;
import cz.spagetka.authenticationservice.model.document.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private final JwtProperties jwtProperties;

    @Override
    public String extractUsername(String jwtToken) {
        return this.extractAllClaims(jwtToken)
                .getSubject();
    }

    @Override
    public String generateJwtToken(UserDetails userDetails) {
        return this.generateJwtToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateJwtToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtProperties.secondsLength())))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isTokenExpired(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }catch (SignatureException | MalformedJwtException | UnsupportedJwtException |
                IllegalArgumentException e) {
            throw new InvalidJwtException(String.format("Passed Jwt not in valid state: %s", e.getMessage()));
        }
    }

    @Override
    public boolean isJwtTokenAssociatedWithUser(String jwtToken, User possibleTokenOwner) {
        String userJwtToken = possibleTokenOwner.getJWT()
                .orElse("");

        return userJwtToken.equals(jwtToken);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret_key());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

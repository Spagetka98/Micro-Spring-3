package cz.spagetka.authenticationservice.security.filter;

import cz.spagetka.authenticationservice.exception.JwtExpirationException;
import cz.spagetka.authenticationservice.properties.JwtProperties;
import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.exception.UserNotFoundException;
import cz.spagetka.authenticationservice.repository.UserRepository;
import cz.spagetka.authenticationservice.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenServiceImpl;
    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        getCookieValueByName(request, jwtProperties.cookie_name())
                .ifPresent((jwt) -> {
                    if (jwtTokenServiceImpl.isTokenValid(jwt)) {
                        this.checkJwtAssociation(jwt, request);
                    } else if (this.jwtTokenServiceImpl.isTokenExpired(jwt)) {
                        throw new JwtExpirationException("JWT is expired!");
                    }
                });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);

        if (cookie != null) {
            return Optional.of(cookie.getValue());
        } else {
            return Optional.empty();
        }
    }

    private void checkJwtAssociation(String jwtToken, HttpServletRequest request) {
        String username = jwtTokenServiceImpl.extractUsername(jwtToken);

        User loadedUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("Could not find a user with username: %s ", username)));

        if (jwtTokenServiceImpl.isJwtTokenAssociatedWithUser(jwtToken, loadedUser)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loadedUser,
                    null,
                    loadedUser.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

    }
}

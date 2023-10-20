package cz.spagetka.newsService.security.filter;

import cz.spagetka.newsService.exception.IllegalRequestInputException;
import cz.spagetka.newsService.exception.IllegalUserRoleException;
import cz.spagetka.newsService.model.dto.UserInformation;
import cz.spagetka.newsService.model.enums.EHeaders;
import cz.spagetka.newsService.model.enums.ERole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        UserInformation userInformation = this.getUserData(request);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInformation, null, userInformation.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UserInformation getUserData(HttpServletRequest request) {
        try {
            String userId = request.getHeader(EHeaders.USER_ID.getValue());;
            String username = request.getHeader(EHeaders.USERNAME.getValue());
            String email = request.getHeader(EHeaders.EMAIL.getValue());

            ERole userRole = ERole.valueOf(request.getHeader(EHeaders.ROLE.getValue()));

            boolean isAccountNonExpired = Boolean.parseBoolean(request.getHeader(EHeaders.IS_ACCOUNT_NON_EXPIRED.getValue()));
            boolean isAccountNonLocked = Boolean.parseBoolean(request.getHeader(EHeaders.IS_ACCOUNT_NON_LOCKED.getValue()));
            boolean isCredentialsNonExpired = Boolean.parseBoolean(request.getHeader(EHeaders.IS_CREDENTIALS_NON_EXPIRED.getValue()));
            boolean isEnabled = Boolean.parseBoolean(request.getHeader(EHeaders.IS_ENABLED.getValue()));

            return new UserInformation(
                    userId, username, email,
                    userRole,
                    isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled
            );
        } catch (ConstraintViolationException ex) {
            throw new IllegalRequestInputException("Received request headers(userId, username, email) cannot be null or empty !");
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalUserRoleException("Unknown received role in request header and it cannot be mapped !");
        } catch (Exception ex) {
            throw new IllegalArgumentException("The user cannot be authenticated due to an error in the received request !");
        }
    }
}

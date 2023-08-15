package cz.spagetka.authenticationservice.model.response;

public record GatewayValidationResponse(
        String userId, String username, String email, String userRole,
        boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
}

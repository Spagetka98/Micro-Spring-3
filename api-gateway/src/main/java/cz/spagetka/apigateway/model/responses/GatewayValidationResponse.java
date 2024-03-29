package cz.spagetka.apigateway.model.responses;

public record GatewayValidationResponse(
        String userId, String username, String email, String userRole,
        boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
}

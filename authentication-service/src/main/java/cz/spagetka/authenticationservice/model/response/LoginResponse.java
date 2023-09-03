package cz.spagetka.authenticationservice.model.response;

import cz.spagetka.authenticationservice.model.enums.ERole;

public record LoginResponse(String userId, String username, String email, ERole role) {
}

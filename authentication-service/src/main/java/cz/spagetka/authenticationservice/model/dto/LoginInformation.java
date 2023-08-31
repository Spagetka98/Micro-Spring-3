package cz.spagetka.authenticationservice.model.dto;

import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.model.enums.ERole;

public record LoginInformation(String userId, String username, String email, ERole role, String jwtToken, RefreshToken refreshToken) {
}

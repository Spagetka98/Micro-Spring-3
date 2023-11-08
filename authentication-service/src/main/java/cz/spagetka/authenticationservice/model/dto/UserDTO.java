package cz.spagetka.authenticationservice.model.dto;

import cz.spagetka.authenticationservice.model.enums.ERole;

public record UserDTO(
        String userId,
        String username,
        String firstName,
        String lastName,
        String email,
        ERole role
) {
}

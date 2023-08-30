package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.model.dto.LoginInformation;
import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import cz.spagetka.authenticationservice.model.response.LoginResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {
    void registerUser(@NotNull(message = "Parameter request cannot be null!") RegisterRequest request);

    LoginInformation loginUser(@NotNull(message = "Parameter request cannot be null!") LoginRequest request);

    String getNonExpiredUserJwtToken(@NotNull(message = "Parameter user cannot be null!") User user);

    RefreshToken getNonExpiredUserRefreshToken(@NotNull(message = "Parameter user cannot be null!") User user)    ;

    String renewUserJwtToken(@NotBlank(message = "Parameter refreshToken cannot be null or empty!") String refreshToken);

    void logout(@NotNull(message = "Parameter user cannot be null!") User user);
}

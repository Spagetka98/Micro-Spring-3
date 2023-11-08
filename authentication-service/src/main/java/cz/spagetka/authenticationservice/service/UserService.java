package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {
    void register(@Valid @NotNull(message = "Parameter request cannot be null!") RegisterRequest request);

    User login(@Valid @NotNull(message = "Parameter request cannot be null!") LoginRequest request);

    String getNonExpiredUserJwtToken(@NotNull(message = "Parameter user cannot be null!") User user);

    RefreshToken getNonExpiredUserRefreshToken(@NotNull(message = "Parameter user cannot be null!") User user)    ;

    String renewJWT(@NotBlank(message = "Parameter refreshToken cannot be null or empty!") String refreshToken);

    void logout(@NotNull(message = "Parameter user cannot be null!") User user);

    void emailConfirmation(@NotBlank(message =  "Parameter verificationToken cannot be null or empty!") String verificationToken);

    void sendEmailForPasswordReset(@NotBlank(message =  "Parameter userEmail cannot be null or empty!") String userEmail);

    void resetPasswordViaToken(
            @NotBlank(message =  "Parameter passwordToken cannot be null or empty!")String passwordToken,
            @NotBlank(message =  "Parameter newPassword cannot be null or empty!")String newPassword);

    User findUser(@NotBlank(message =  "Parameter userId cannot be null or empty!") String userId);
}

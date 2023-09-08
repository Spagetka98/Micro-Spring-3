package cz.spagetka.authenticationservice.service;

import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.model.dto.LoginInformation;
import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {
    void register(@NotNull(message = "Parameter request cannot be null!") RegisterRequest request);

    LoginInformation login(@NotNull(message = "Parameter request cannot be null!") LoginRequest request);

    String getNonExpiredUserJwtToken(@NotNull(message = "Parameter user cannot be null!") User user);

    RefreshToken getNonExpiredUserRefreshToken(@NotNull(message = "Parameter user cannot be null!") User user)    ;

    String renewUserJwtToken(@NotBlank(message = "Parameter refreshToken cannot be null or empty!") String refreshToken);

    void logout(@NotNull(message = "Parameter user cannot be null!") User user);

    void userEmailVerification(@NotBlank(message =  "Parameter verificationToken cannot be null or empty!") String verificationToken);

    void sendResetRequest(@NotBlank(message =  "Parameter userEmail cannot be null or empty!") String userEmail);

    void resetPassword(
            @NotBlank(message =  "Parameter passwordToken cannot be null or empty!")String passwordToken,
            @NotBlank(message =  "Parameter oldPassword cannot be null or empty!")String oldPassword,
            @NotBlank(message =  "Parameter newPassword cannot be null or empty!")String newPassword);
}

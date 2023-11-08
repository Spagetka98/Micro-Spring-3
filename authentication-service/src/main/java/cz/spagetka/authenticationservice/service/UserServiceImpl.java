package cz.spagetka.authenticationservice.service;

import com.mongodb.MongoWriteException;
import cz.spagetka.authenticationservice.exception.mongo.MongoDuplicateKeyException;
import cz.spagetka.authenticationservice.exception.passwordToken.MissingPasswordTokenException;
import cz.spagetka.authenticationservice.exception.passwordToken.PasswordTokenExpirationException;
import cz.spagetka.authenticationservice.exception.refreshToken.InvalidRefreshTokenException;
import cz.spagetka.authenticationservice.exception.refreshToken.RefreshTokenExpirationException;
import cz.spagetka.authenticationservice.exception.user.UserInformationTaken;
import cz.spagetka.authenticationservice.exception.user.UserNotFoundException;
import cz.spagetka.authenticationservice.exception.verificationToken.MissingVerificationTokenException;
import cz.spagetka.authenticationservice.exception.verificationToken.VerificationTokenExpirationException;
import cz.spagetka.authenticationservice.model.document.embedded.PasswordToken;
import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.model.document.embedded.VerificationToken;
import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.enums.ERole;
import cz.spagetka.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;
    private final VerificationTokenService verificationTokenService;
    private final PasswordTokenService passwordTokenService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        if (this.userRepository.existsByUsername(request.username()))
            throw new UserInformationTaken(String.format("Username %s is taken by another user!", request.username()));

        if (this.userRepository.existsByEmail(request.email()))
            throw new UserInformationTaken(String.format("Email %s is taken by another user!", request.email()));

        try {
            this.createNewUserAccount(request);
        } catch (MongoWriteException e) {
            throw new MongoDuplicateKeyException("Write operation failed due to unique index error!");
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred while saving user!",e);
        }

    }

    @Override
    @Transactional
    public User login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        User user = (User) authentication.getPrincipal();

        this.getNonExpiredUserJwtToken(user);

        this.getNonExpiredUserRefreshToken(user);

        return user;
    }

    @Override
    public void logout(User user) {
        user.removeJWT();

        user.removeRefreshToken();

        this.userRepository.save(user);
    }

    @Override
    public void emailConfirmation(String verificationToken) {
        User user = this.userRepository.findByVerificationToken(verificationToken)
                .orElseThrow(() -> new MissingVerificationTokenException(String.format("Could not find a user with verification token: %s", verificationToken)));

        if (this.verificationTokenService.isVerificationTokenExpired(user.getVerificationToken().get()))
            throw new VerificationTokenExpirationException(String.format("Verification token of user with id: %s", user.getUserId().toString()));

        user.setEnabled(true);

        user.removeVerificationToken();

        this.userRepository.save(user);
    }

    @Override
    public void sendEmailForPasswordReset(String userEmail) {
        User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new UserNotFoundException(String.format("User with email: %s was not found!",userEmail)));

        PasswordToken passwordToken = this.passwordTokenService.createPasswordToken();

        user.setPasswordToken(passwordToken);

        this.userRepository.save(user);

        this.emailService.sendPasswordResetEmail(userEmail,passwordToken.getToken());
    }

    @Override
    public void resetPasswordViaToken(String passwordToken, String newPassword) {
        User user = this.userRepository.findByPasswordToken(passwordToken)
                .orElseThrow(() -> new MissingPasswordTokenException(String.format("Could not find a user with password token: %s", passwordToken)));

        if(this.passwordTokenService.isPasswordTokenExpired(user.getPasswordToken().get()))
            throw new PasswordTokenExpirationException(String.format("Password token of user with id: %s is expired!", user.getUserId().toString()));

        user.setPassword(this.passwordEncoder.encode(newPassword));

        user.removePasswordToken();

        this.userRepository.save(user);
    }

    @Override
    public User findUser(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s was not found in database!",userId)));
    }

    @Override
    public String getNonExpiredUserJwtToken(User user) {
        if (user.getJWT().isEmpty() || this.jwtTokenService.isTokenExpired(user.getJWT().get())) {
            String jwtToken = this.jwtTokenService.generateJwtToken(user);

            user.setJWT(jwtToken);

            this.userRepository.save(user);

            return jwtToken;
        } else
            return user.getJWT().get();
    }

    @Override
    public RefreshToken getNonExpiredUserRefreshToken(User user) {
        if (user.getRefreshToken().isEmpty() || this.refreshTokenService.isTokenExpired(user.getRefreshToken().get())) {
            RefreshToken refreshToken = this.refreshTokenService.generateRefreshToken();

            user.setRefreshToken(refreshToken);

            this.userRepository.save(user);

            return refreshToken;
        } else
            return user.getRefreshToken().get();
    }

    @Override
    public String renewJWT(String refreshToken) {
        User user = this.userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UserNotFoundException(String.format("Cannot find a user with refresh token: %s", refreshToken)));

        RefreshToken retrivedRefreshToken = user.getRefreshToken()
                .orElseThrow(() -> new InvalidRefreshTokenException("Current user does not have a Refresh Token!"));

        if (this.refreshTokenService.isTokenExpired(retrivedRefreshToken))
            throw new RefreshTokenExpirationException("Current user has an expired Refresh Token!");
        else
            return this.getNonExpiredUserJwtToken(user);
    }

    private void createNewUserAccount(RegisterRequest request) {
        User newUser = User.builder()
                .username(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(ERole.ROLE_USER)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(false)
                .build();

        VerificationToken newToken = this.verificationTokenService.createVerificationToken();

        newUser.setVerificationToken(newToken);

        User registeredUser = this.userRepository.save(newUser);

        this.emailService.sendUserVerificationEmail(registeredUser.getEmail(), newToken.getToken());
    }

}

package cz.spagetka.authenticationservice.service;

import com.mongodb.MongoWriteException;
import cz.spagetka.authenticationservice.exception.*;
import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.model.dto.LoginInformation;
import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import cz.spagetka.authenticationservice.model.response.LoginResponse;
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
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {
        if (this.userRepository.existsByUsername(request.username()))
            throw new UserInformationTaken(String.format("Username %s is taken by another user!", request.username()));

        if (this.userRepository.existsByEmail(request.email()))
            throw new UserInformationTaken(String.format("Email %s is taken by another user!", request.email()));

        try {
            User newUser = User.builder()
                    .username(request.username())
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .role(ERole.USER)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .build();

            this.userRepository.save(newUser);
        } catch (MongoWriteException e) {
            throw new MongoDuplicateKeyException("Write operation error due to duplicate index key!");
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred while saving user!");
        }

    }

    @Override
    @Transactional
    public LoginInformation loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        User loggedUser = (User) authentication.getPrincipal();

        String jwtToken = this.getNonExpiredUserJwtToken(loggedUser);

        RefreshToken refreshToken = this.getNonExpiredUserRefreshToken(loggedUser);

        return new LoginInformation(loggedUser.getUsername(), loggedUser.getEmail(), loggedUser.getRole(), jwtToken, refreshToken);
    }

    @Override
    public void logout(User user) {
        user.removeJWT();

        user.removeRefreshToken();

        this.userRepository.save(user);
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
    public String renewUserJwtToken(String refreshToken) {
        User user = this.userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UserNotFoundException(String.format("Cannot find a user with refresh token: %s", refreshToken)));

        RefreshToken retrivedRefreshToken = user.getRefreshToken()
                .orElseThrow(() -> new InvalidRefreshTokenException("Current user does not have a Refresh Token!"));

        if (this.refreshTokenService.isTokenExpired(retrivedRefreshToken))
            throw new RefreshTokenExpirationException("Current user has an expired Refresh Token!");
        else
            return this.getNonExpiredUserJwtToken(user);
    }
}

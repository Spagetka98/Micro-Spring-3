package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.dto.LoginInformation;
import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import cz.spagetka.authenticationservice.model.response.LoginResponse;
import cz.spagetka.authenticationservice.service.CookieService;
import cz.spagetka.authenticationservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final CookieService cookieService;

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody RegisterRequest request) {
        this.userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request){
        LoginInformation loginInformation = this.userService.loginUser(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieService.getJwtCookie(loginInformation.jwtToken()).toString())
                .header(HttpHeaders.SET_COOKIE, cookieService.getJwRefreshTokenCookie(loginInformation.refreshToken()).toString())
                .body(new LoginResponse(loginInformation.username(), loginInformation.username(), loginInformation.role()));
    }
}

package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.mapper.UserMapper;
import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.dto.UserDTO;
import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import cz.spagetka.authenticationservice.service.CookieService;
import cz.spagetka.authenticationservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final CookieService cookieService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest request) {
        this.userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest request) {
        User user = this.userService.login(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieService.getJwtCookie(user.getJWT().get()).toString())
                .header(HttpHeaders.SET_COOKIE, cookieService.getJwRefreshTokenCookie(user.getRefreshToken().get()).toString())
                .body(userMapper.toDTO(user));
    }
}

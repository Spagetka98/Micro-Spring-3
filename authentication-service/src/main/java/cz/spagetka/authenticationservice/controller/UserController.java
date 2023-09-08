package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.request.MessageResponse;
import cz.spagetka.authenticationservice.service.CookieService;
import cz.spagetka.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secured/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CookieService cookieService;

    @GetMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@AuthenticationPrincipal User user){
        this.userService.logout(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,cookieService.cleanJwtCookie().toString())
                .header(HttpHeaders.SET_COOKIE,cookieService.cleanRefreshTokenCookie().toString())
                .body(new MessageResponse("Logout was successfully!"));
    }

}

package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.response.MessageResponse;
import cz.spagetka.authenticationservice.service.CookieService;
import cz.spagetka.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CookieService cookieService;

    @GetMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@AuthenticationPrincipal User user){
        this.userService.logout(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,this.cookieService.cleanJwtCookie().toString())
                .header(HttpHeaders.SET_COOKIE,this.cookieService.cleanRefreshTokenCookie().toString())
                .body(new MessageResponse("Logout was successful!"));
    }

}

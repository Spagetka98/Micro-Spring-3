package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.response.MessageResponse;
import cz.spagetka.authenticationservice.service.CookieService;
import cz.spagetka.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/token")
@RequiredArgsConstructor
public class RefreshController {
    private final UserService userService;
    private final CookieService cookieService;

    @GetMapping("/refreshJWT")
    public ResponseEntity<MessageResponse> refreshJWT(@CookieValue(name = "RefreshToken") String refreshToken){
       String newJwt = this.userService.renewUserJwtToken(refreshToken);

       return ResponseEntity.ok()
               .header(HttpHeaders.SET_COOKIE,cookieService.getJwtCookie(newJwt).toString())
               .body(new MessageResponse("Token is refreshed successfully!"));
    }
}
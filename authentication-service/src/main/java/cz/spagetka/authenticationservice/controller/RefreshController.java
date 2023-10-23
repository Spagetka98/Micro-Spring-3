package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.response.MessageResponse;
import cz.spagetka.authenticationservice.service.CookieService;
import cz.spagetka.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/refresh")
@RequiredArgsConstructor
public class RefreshController {
    private final UserService userService;
    private final CookieService cookieService;

    @GetMapping("/jwt")
    public ResponseEntity<MessageResponse> refreshJWT(@CookieValue(name = "RefreshToken") String refreshToken){
       String newJwt = this.userService.renewJWT(refreshToken);

       return ResponseEntity.ok()
               .header(HttpHeaders.SET_COOKIE,this.cookieService.getJwtCookie(newJwt).toString())
               .body(new MessageResponse("Token is refreshed successfully!"));
    }
}
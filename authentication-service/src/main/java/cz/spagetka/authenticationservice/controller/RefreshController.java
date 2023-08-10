package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.request.RefreshRequest;
import cz.spagetka.authenticationservice.model.request.RefreshTokenResponse;
import cz.spagetka.authenticationservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/token")
@RequiredArgsConstructor
public class RefreshController {
    private final UserService userService;

    @PostMapping("/refreshJWT")
    public RefreshTokenResponse refreshJWT(@Valid @RequestBody RefreshRequest request){
       String newJwt = this.userService.renewUserJwtToken(request.refreshToken());

       return new RefreshTokenResponse(newJwt);
    }
}
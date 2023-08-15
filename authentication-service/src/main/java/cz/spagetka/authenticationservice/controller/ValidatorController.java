package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.response.GatewayValidationResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class ValidatorController {

    @GetMapping("/validation")
    public GatewayValidationResponse requestGatewayValidation(@AuthenticationPrincipal User user){
        return new GatewayValidationResponse(
                user.getUserId().toString(),user.getUsername(), user.getEmail(),
                user.getRole().toString(),
                user.isAccountNonExpired(), user.isAccountNonLocked(), user.isCredentialsNonExpired(),user.isEnabled());
    }
}

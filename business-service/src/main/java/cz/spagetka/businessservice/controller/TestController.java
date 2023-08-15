package cz.spagetka.businessservice.controller;

import cz.spagetka.businessservice.model.dto.UserInformation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/endpoint")
    @PreAuthorize("hasAuthority('USER')")
    public UserInformation getHello(@AuthenticationPrincipal UserInformation userInformation){
        return userInformation;
    }

}

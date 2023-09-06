package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/email")
@RequiredArgsConstructor
public class EmailController {
    private final UserService userService;

    @GetMapping("/emailVerification/{verificationToken}")
    public void emailVerification(@PathVariable("verificationToken") String verificationToken){
        this.userService.userEmailVerification(verificationToken);
    }
}

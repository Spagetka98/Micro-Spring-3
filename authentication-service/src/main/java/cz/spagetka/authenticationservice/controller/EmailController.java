package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/email")
@RequiredArgsConstructor
public class EmailController {
    private final UserService userService;

    @GetMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void emailConfirmation(@RequestParam(value = "token") String token){
        this.userService.emailConfirmation(token);
    }
}

package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.request.LoginRequest;
import cz.spagetka.authenticationservice.model.request.RegisterRequest;
import cz.spagetka.authenticationservice.model.response.LoginResponse;
import cz.spagetka.authenticationservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody RegisterRequest request) {
        this.userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@Valid @RequestBody LoginRequest request){
        return this.userService.loginUser(request);
    }
}

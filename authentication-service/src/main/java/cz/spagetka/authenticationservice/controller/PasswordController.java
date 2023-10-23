package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.request.ForgotPasswordRequest;
import cz.spagetka.authenticationservice.model.request.ResetPasswordRequest;
import cz.spagetka.authenticationservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/password")
@RequiredArgsConstructor
public class PasswordController {
    private final UserService userService;

    @PostMapping("/forgot")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendPasswordResetEmail(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest){
        this.userService.sendEmailForPasswordReset(forgotPasswordRequest.email());
    }

    @PostMapping("/reset-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPasswordViaToken(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        this.userService.resetPasswordViaToken(resetPasswordRequest.passwordToken(), resetPasswordRequest.newPassword());
    }
}

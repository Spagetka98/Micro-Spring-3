package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.request.ForgetPasswordRequest;
import cz.spagetka.authenticationservice.model.request.ResetPasswordRequest;
import cz.spagetka.authenticationservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/password")
@RequiredArgsConstructor
public class PasswordController {
    private final UserService userService;

    @PostMapping("/sendResetEmail")
    public void sendResetRequest(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest){
        this.userService.sendResetRequest(forgetPasswordRequest.email());
    }

    @PostMapping("/resetPasswordByToken")
    public void resetPasswordByToken(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        this.userService.resetPasswordWithToken(resetPasswordRequest.passwordToken(),resetPasswordRequest.newPassword());
    }
}

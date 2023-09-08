package cz.spagetka.authenticationservice.controller;

import cz.spagetka.authenticationservice.model.request.ForgetPasswordRequest;
import cz.spagetka.authenticationservice.model.request.ResetPasswordRequest;
import cz.spagetka.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/password")
@RequiredArgsConstructor
public class PasswordController {
    private final UserService userService;

    @PostMapping("/sendResetEmail")
    public void sendResetRequest(@RequestBody ForgetPasswordRequest forgetPasswordRequest){
        this.userService.sendResetRequest(forgetPasswordRequest.email());
    }

    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        this.userService.resetPassword(resetPasswordRequest.passwordToken(),resetPasswordRequest.oldPassword(),resetPasswordRequest.newPassword());
    }
}

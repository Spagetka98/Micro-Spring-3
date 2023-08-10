package cz.spagetka.authenticationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class ValidatorController {

    @GetMapping("/validation")
    public String getHello(){
        return "Hello";
    }
}

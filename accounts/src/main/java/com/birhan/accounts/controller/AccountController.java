package com.birhan.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("say-hello")
    public String SayHello(){
        return "Hi World!";
    }
}

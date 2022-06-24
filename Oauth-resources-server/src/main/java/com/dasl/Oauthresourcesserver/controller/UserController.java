package com.dasl.Oauthresourcesserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/users")
    public String[] getUSers() {
        return new String[]{"Diogo","Andy"};
    }
}

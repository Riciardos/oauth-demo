package com.matchesfashion.oauthdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class OAuthController
{

    @RequestMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.of(Optional.of("Hello and welcome to my demo"));
    }


}

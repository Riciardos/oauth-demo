package com.matchesfashion.oauthdemo.controller;

import com.matchesfashion.oauthdemo.data.AccessToken;
import com.matchesfashion.oauthdemo.data.AuthorizationCode;
import com.matchesfashion.oauthdemo.service.GithubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
public class OAuthController
{

    @RequestMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.of(Optional.of("Hello and welcome to my demo"));
    }

    @Autowired
    GithubClient githubClient;

    @RequestMapping("/authorizationcode")
    public ResponseEntity<AuthorizationCode> authorizationCode() {
        String authCode = githubClient.getAuthorizationCode();
        AuthorizationCode code = new AuthorizationCode(authCode);
        return ResponseEntity.of(Optional.of(code));
    }

    @RequestMapping("/exchange")
    public ResponseEntity<AccessToken> exchangeAuthCodeForToken(@RequestParam String code) {
        AccessToken token = new AccessToken("testtoken");
        return ResponseEntity.of(Optional.of(token));
    }

}

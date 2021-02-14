package com.matchesfashion.oauthdemo;

import com.matchesfashion.oauthdemo.data.AccessToken;
import com.matchesfashion.oauthdemo.data.AuthorizationCode;
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


    @RequestMapping("/authorizationcode")
    public ResponseEntity<AuthorizationCode> authorizationCode() {
        AuthorizationCode code = new AuthorizationCode("testcode");
        return ResponseEntity.of(Optional.of(code));
    }

    @RequestMapping("/exchange")
    public ResponseEntity<AccessToken> exchangeAuthCodeForToken(@RequestParam String code) {
        AccessToken token = new AccessToken("testtoken");
        return ResponseEntity.of(Optional.of(token));
    }

}

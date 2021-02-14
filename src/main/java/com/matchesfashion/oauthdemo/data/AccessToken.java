package com.matchesfashion.oauthdemo.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AccessToken {

    String token;

    public AccessToken(String token) {
        this.token = token;
    }
}

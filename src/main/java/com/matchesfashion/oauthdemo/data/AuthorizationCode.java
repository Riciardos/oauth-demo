package com.matchesfashion.oauthdemo.data;

import lombok.Data;

@Data
public class AuthorizationCode
{
    String code;

    public AuthorizationCode(String code) {
        this.code = code;
    }
}

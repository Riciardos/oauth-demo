package com.matchesfashion.oauthdemo.security;

import lombok.Data;

@Data
public class UserPrincipal
{
    private String userId;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
}

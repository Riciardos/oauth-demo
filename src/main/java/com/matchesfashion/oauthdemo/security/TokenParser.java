package com.matchesfashion.oauthdemo.security;

import org.springframework.security.core.AuthenticationException;

public interface TokenParser {
    UserPrincipal parseToken(String token) throws AuthenticationException;
}

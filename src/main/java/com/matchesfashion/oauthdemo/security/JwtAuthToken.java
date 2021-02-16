package com.matchesfashion.oauthdemo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * AuthenticationToken containing a Hybris Session ID to be used for authentication and retrieving user details
 */
public class JwtAuthToken extends UsernamePasswordAuthenticationToken {
    public JwtAuthToken(Object principal) {
        super(principal, null);
    }
}

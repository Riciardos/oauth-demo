package com.matchesfashion.oauthdemo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthProvider implements AuthenticationProvider {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final TokenParser tokenParser;

    public JwtAuthProvider(TokenParser jwtDecoder) {
        this.tokenParser = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserPrincipal user = tokenParser.parseToken((String) authentication.getPrincipal());
        return new UsernamePasswordAuthenticationToken(user, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.isAssignableFrom(authentication);
    }
}

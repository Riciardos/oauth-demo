package com.matchesfashion.oauthdemo.security;

import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.VerificationKeyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * JWT decoder. JWT must have an asymmetric signature. The signature must be created with a private key not shared
 * beyond the service creating the JWT. This class uses the public key to verify the signature.
 * <p>
 * Public keys are periodically fetched from a HTTPS endpoint. The endpoint should expose the keys following the JWKS
 * standard. Multiple keys may be active to facilitate key rotation. Each public key has a unique ID and the JWT needs
 * to contain the ID of its corresponding key.
 */

@Component
public class JwksJwtDecoder implements TokenParser {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final JwtConsumer jwtConsumer;
    private String googleAud;

    @Autowired
    public JwksJwtDecoder(@Value("${google.auth.authServerJwksUrl}") String authServerJwksUrl,
                          @Value("${google.auth.audience}") String googleAud) {
        HttpsJwks httpsJkws = new HttpsJwks(authServerJwksUrl);
        HttpsJwksVerificationKeyResolver httpsJwksKeyResolver = new HttpsJwksVerificationKeyResolver(httpsJkws);

        this.googleAud = googleAud;
        this.jwtConsumer = jwtConsumer(httpsJwksKeyResolver);
    }

    JwksJwtDecoder(VerificationKeyResolver jwksResolver) {
        this.jwtConsumer = jwtConsumer(jwksResolver);
    }

    private JwtConsumer jwtConsumer(VerificationKeyResolver jwksResolver) {
        LOG.info("Google audience: {}", googleAud);
        return new JwtConsumerBuilder()
                .setExpectedAudience(googleAud)
                .setAllowedClockSkewInSeconds(30)
                .setRequireExpirationTime()
                .setVerificationKeyResolver(jwksResolver)
                .build();
    }

    @Override
    public UserPrincipal parseToken(String token) throws AuthenticationException {
        try {
            JwtContext jwtContext = jwtConsumer.process(token);
            JwtClaims claims = jwtContext.getJwtClaims();

            UserPrincipal user = new UserPrincipal();
            user.setUserId(claims.getClaimValue("sub", String.class));
            user.setEmail(claims.getClaimValue("email", String.class));
            user.setName(claims.getClaimValue("name", String.class));
            user.setGivenName(claims.getClaimValue("given_name", String.class));
            user.setFamilyName(claims.getClaimValue("family_name", String.class));

            return user;

        } catch (InvalidJwtException | MalformedClaimException ex) {
            LOG.error("Exception parsing JWT token: {}", token, ex);
            throw new BadCredentialsException("Exception parsing JWT token: " + token, ex);
        }
    }
}

package com.online.shop.ecombackend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.online.shop.ecombackend.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for handling JWTs for user authentication.
 */
@Service
public class JWTService {
    /** The secret key to encrypt the JWTs with. */
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    /** The issuer the JWT is signed with. */
    @Value("${jwt.issuer}")
    private String issuer;
    /** How many seconds from generation should the JWT expire? */
    @Value("${jwt.expiryInSeconds}")
    private long expiryInSeconds;
    /** The algorithm generated post construction. */
    private Algorithm algorithm;
    /** The JWT claim key for the username. */
    private static final String USERNAME_KEY = "USERNAME";

    private static final String VERIFICATION_EMAIL_KEY = "VERIFICATION_EMAIL";
    private static final String RESET_PASSWORD_EMAIL_KEY = "RESET_PASSWORD_EMAIL";

    /**
     * Post construction method.
     */
    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    /**
     * Generates a JWT based on the given user.
     * @param user The user to generate for.
     * @return The JWT.
     */
    public String generateJWT(User user) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ( 1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String generateVerificationJWT(User user){
         return JWT.create()
                .withClaim(VERIFICATION_EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + ( 1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);

    }

    /**
     * Generates a JWT for use when resetting a password.
     * @param user The user to generate for.
     * @return The generated JWT token.
     */
    public String generatePasswordResetJWT(User user){
        return JWT.create()
                .withClaim(RESET_PASSWORD_EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + ( 1000 * 60 * 15))) //15 minutes
                .withIssuer(issuer)
                .sign(algorithm);

    }
    public String getResetPasswordEmail(String token){
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(RESET_PASSWORD_EMAIL_KEY).asString();

    }

    /**
     * Gets the username out of a given JWT.
     * @param token The JWT to decode.
     * @return The username stored inside.
     */
    public String getUsername(String token) {
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(USERNAME_KEY).asString();
    }


}

package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTServiceTest {

    /** The JWTService to test. */
    @Autowired
    private JWTService jwtService;
    /** The Local User DAO. */
    @Autowired
    private UserRepository userRepository;

    /**
     * Tests that the verification token is not usable for login.
     */
    @Test
    public void testVerificationTokenNotUsableForLogin() {
        User user = userRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJWT(user);
        Assertions.assertNull(jwtService.getUsername(token), "Verification token should not contain username.");
    }

    /**
     * Tests that the authentication token generate still returns the username.
     */
    @Test
    public void testAuthTokenReturnsUsername() {
        User user = userRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        Assertions.assertEquals(user.getUsername(), jwtService.getUsername(token), "Token for auth should contain users username.");
    }


}

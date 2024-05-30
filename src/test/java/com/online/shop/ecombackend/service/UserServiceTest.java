package com.online.shop.ecombackend.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.online.shop.ecombackend.dto.Login;
import com.online.shop.ecombackend.dto.PasswordReset;
import com.online.shop.ecombackend.dto.Registration;
import com.online.shop.ecombackend.exception.EmailFailureException;
import com.online.shop.ecombackend.exception.EmailNotFoundException;
import com.online.shop.ecombackend.exception.UserException;
import com.online.shop.ecombackend.exception.UserNotVerifiedException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot","secret"))
            .withPerMethodLifecycle(true); //for every methode that run will wipe email
    @Autowired
    private  UserService userService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private JWTService jwtService;


    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        Registration registration = new Registration();
        registration.setUsername("UserA");
        registration.setEmail("UserServiceTest$testRegisterUser@junit.com");
        registration.setFirstname("FirstName");
        registration.setLastname("LastName");
        registration.setPassword("MySecretPassword123");
        Assertions.assertThrows(UserException.class,
                () -> userService.registerUser(registration), "Username should already be in use.");
        registration.setUsername("UserServiceTest$testRegisterUser");
        registration.setEmail("UserA@junit.com");
        Assertions.assertThrows(UserException.class,
                () -> userService.registerUser(registration),"Email should already be in use.");
       registration.setEmail("UserServiceTest$testRegisterUser@junit.com");
        Assertions.assertDoesNotThrow(
                () -> userService.registerUser(registration),"User should register successfully.");
        Assertions.assertEquals(registration.getEmail(),
                greenMailExtension.getReceivedMessages()[0]
                        .getRecipients(Message.RecipientType.TO)[0].toString());
    }

    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
        Login login = new Login();
        login.setUsername("UserA-NotExists");
        login.setPassword("PasswordA123-BadPassword");
        Assertions.assertNull(userService.loginUser(login), "The user should not exist.");
        login.setUsername("UserA");
        Assertions.assertNull(userService.loginUser(login), "The password should be incorrect.");
        login.setPassword("PasswordA123");
        Assertions.assertNotNull(userService.loginUser(login), "The user should login successfully.");
        login.setUsername("UserB");
        login.setPassword("PasswordB123");
        try {
            userService.loginUser(login);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertTrue(ex.isNewEmailSent(), "Email verification should be sent.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
        try {
            userService.loginUser(login);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertFalse(ex.isNewEmailSent(), "Email verification should not be resent.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }

    }

    /**
     * Tests the verifyUser method.
     * @throws EmailFailureException
     */
    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"), "Token that is bad or does not exist should return false.");
        Login login = new Login();
        login.setUsername("UserB");
        login.setPassword("PasswordB123");
        try {
            userService.loginUser(login);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            List<VerficationToken> tokens = verificationTokenRepository.findByUser_IdOrderByIdDesc(2L);
            String token = tokens.get(0).getToken();
            Assertions.assertTrue(userService.verifyUser(token), "Token should be valid.");
            Assertions.assertNotNull(login, "The user should now be verified.");
        }
    }

    /**
     * Tests the forgotPassword method in the User Service.
     * @throws MessagingException
     */
    @Test
    @Transactional
    public void testForgotPassword() throws MessagingException {
        Assertions.assertThrows(EmailNotFoundException.class,
                () -> userService.forgotPassword("UserNotExist@junit.com"));
        Assertions.assertDoesNotThrow(() -> userService.forgotPassword(
                "UserA@junit.com"), "Non existing email should be rejected.");
        Assertions.assertEquals("UserA@junit.com",
                greenMailExtension.getReceivedMessages()[0]
                        .getRecipients(Message.RecipientType.TO)[0].toString(), "Password " +
                        "reset email should be sent.");
    }

    /**
     * Tests the resetPassword method in the User Service.
     * @throws MessagingException
     */
    @Test
    public void testResetPassword() {
        User user = userRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generatePasswordResetJWT(user);
        PasswordReset body = new PasswordReset();
        body.setToken(token);
        body.setPassword("Password1234");
        userService.resetPassword(body);
        user = userRepository.findByUsernameIgnoreCase("UserA").get();
        Assertions.assertTrue(encryptionService.verifyPassword("Password1234",
                user.getPassword()), "Password change should be written to DB.");
    }




}

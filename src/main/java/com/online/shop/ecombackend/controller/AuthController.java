package com.online.shop.ecombackend.controller;

import com.online.shop.ecombackend.dto.Login;
import com.online.shop.ecombackend.dto.LoginResponse;
import com.online.shop.ecombackend.dto.PasswordReset;
import com.online.shop.ecombackend.dto.Registration;
import com.online.shop.ecombackend.exception.EmailFailureException;
import com.online.shop.ecombackend.exception.EmailNotFoundException;
import com.online.shop.ecombackend.exception.UserException;
import com.online.shop.ecombackend.exception.UserNotVerifiedException;
import com.online.shop.ecombackend.model.User;
import com.online.shop.ecombackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/auth")
public class AuthController {


    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody Registration registration) throws UserException, EmailFailureException {
        try {
            System.out.println(registration);
            userService.registerUser(registration);
            return ResponseEntity.ok().build();
        } catch (UserException e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    };


    /**
     * Post Mapping to handle user logins to provide authentication token.
     * @param login The login information.
     * @return The authentication token if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody Login login) {
        String jwt = null;
        try {
            jwt = userService.loginUser(login);
        } catch (UserNotVerifiedException e) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERFIED";
            if(e.isNewEmailSent()){
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }


    /**
     * Post mapping to verify the email of an account using the emailed token.
     * @param token The token emailed for verification. This is not the same as a
     *              authentication JWT.
     * @return 200 if successful. 409 if failure.
     */
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //user isn't verified or token doesn't exist
        }
    }

    /**
     * Gets the profile of the currently logged-in user and returns it.
     * @param user The authentication principal object.
     * @return The user profile.
     */
    @GetMapping("/me")
    public User getLoggedInUserProfile(@AuthenticationPrincipal User user) {
        return user;
    }

    /**
     * Sends an email to the user with a link to reset their password.
     * @param email The email to reset.
     * @return Ok if sent, bad request if email not found.
     */
  //  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok().build();
        } catch (EmailNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Resets the users password with the given token and password.
     * @param body The information for the password reset.
     * @return Okay if password was set.
     */
    @PostMapping("/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody PasswordReset body) {
        userService.resetPassword(body);
        return ResponseEntity.ok().build();
    }
}






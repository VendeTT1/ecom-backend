package com.online.shop.ecombackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.dto.LoginBody;
import com.online.shop.ecombackend.dto.RegistrationBody;
import com.online.shop.ecombackend.exception.IncorrectPasswordException;
import com.online.shop.ecombackend.exception.UserNotFoundException;
import com.online.shop.ecombackend.model.User;
import com.online.shop.ecombackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    //    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        try {
//            userService.registerUser(user);
//            return new ResponseEntity<>("Registration Successful", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * Post Mapping to handle registering users.
     * @param registrationBody The registration information.
     * @return Response to front end.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        if (userRepository.findAllById(registrationBody.getId()).isPresent()
                || userRepository.findByNameIgnoreCase(registrationBody.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this ID or name already exists");
        }
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok("Registration successful");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + ex.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginBody loginBody) {
//        try {
//            userService.loginUser(loginBody);
//            return ResponseEntity.ok("Login successful");
//        }
//        catch (UserNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
//        }
//        catch (IncorrectPasswordException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginBody loginBody) {
//        try {
//            User user = userService.loginUser(loginBody); // Assuming userService returns the User object
//            Map<String, Object> response = new HashMap<>();
//            response.put("userId", user.getId());
//            response.put("message", "Login successful");
//            return ResponseEntity.ok(//"Login successful \n
//                    " \"id\":\"" +user.getId()+"\"");
//
//        } catch (UserNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
//        } catch (IncorrectPasswordException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
//        }
//    }
@PostMapping("/login")
public ResponseEntity<Map<String, String>> loginUser(@Valid @RequestBody LoginBody loginBody) {
    try {
        User user = userService.loginUser(loginBody); // Assuming userService returns the User object
        Map<String, String> response = new HashMap<>();
        response.put("id", user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (UserNotFoundException ex) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed: Incorrect Email" + ex.getMessage());
    } catch (IncorrectPasswordException ex) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed: Incorrect Password" + ex.getMessage());
    }
}

}

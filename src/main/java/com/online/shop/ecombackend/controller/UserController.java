package com.online.shop.ecombackend.controller;

import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.exception.UserNotFoundException;
import com.online.shop.ecombackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    Optional<User> getUsertById(@PathVariable String id){
        return  userRepository.findById(id);
    }


    @PutMapping("/edit/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> {
                    if (newUser.getName() != null && !newUser.getName().isEmpty()) {
                        user.setName(newUser.getName());
                    }
                    if (newUser.getEmail() != null && !newUser.getEmail().isEmpty()) {
                        user.setEmail(newUser.getEmail());
                    }
                    if (newUser.getLastname() != null && !newUser.getLastname().isEmpty()) {
                        user.setLastname(newUser.getLastname());
                    }
                    if (newUser.getPhone() != null && !newUser.getPhone().isEmpty()) {
                        user.setPhone(newUser.getPhone());
                    }
                    if (newUser.getAddress() != null && !newUser.getAddress().isEmpty()) {
                        user.setAddress(newUser.getAddress());
                    }
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

}

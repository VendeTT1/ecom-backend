package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.dto.LoginBody;
import com.online.shop.ecombackend.dto.RegistrationBody;
import com.online.shop.ecombackend.exception.IncorrectPasswordException;
import com.online.shop.ecombackend.exception.UserNotFoundException;
import com.online.shop.ecombackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;




    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User registerUser(RegistrationBody registrationBody)  {

        User user = new User();
        user.setId(registrationBody.getId());
        user.setEmail(registrationBody.getEmail());
        user.setName(registrationBody.getName());
        user.setAddress(registrationBody.getAddress());
        user.setPhone(registrationBody.getPhone());
        user.setLastname(registrationBody.getLastname());
//        user.setPassword(registrationBody.getPassword());
        user.setUserWishlist(registrationBody.getUserWishlist());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
//        VerificationToken verificationToken = createVerificationToken(user);

        return userRepository.save(user);
    }

    public User loginUser(LoginBody loginBody) {
        Optional<User> userOpt = userRepository.findAllByEmailIgnoreCase(loginBody.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return user;
            } else {
                throw new IncorrectPasswordException("Incorrect password");
            }
        }
        else {
            throw new UserNotFoundException("User not found with email: " + loginBody.getEmail());
        }
    }
}

package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.dto.RegistrationBody;
import com.online.shop.ecombackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;


    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

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

}

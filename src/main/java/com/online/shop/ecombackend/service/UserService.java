package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.dao.VerificationTokenRepository;
import com.online.shop.ecombackend.dto.Login;
import com.online.shop.ecombackend.dto.Registration;
import com.online.shop.ecombackend.exception.EmailFailureException;
import com.online.shop.ecombackend.exception.UserException;
import com.online.shop.ecombackend.exception.UserNotVerifiedException;
import com.online.shop.ecombackend.model.User;
import com.online.shop.ecombackend.model.VerficationToken;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.tomcat.util.digester.SystemPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private EmailService emailService;
    private VerificationTokenRepository verificationTokenRepository;
    private Optional<User> findUserEmail;
    private Optional<User> findUserName;

    public UserService(UserRepository userRepository,
                       EncryptionService encryptionService,
                       JWTService jwtService,
                       EmailService emailService,
                       VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.verificationTokenRepository = verificationTokenRepository;

    }

    public User registerUser(Registration registration) throws UserException, EmailFailureException {
        findUserEmail = userRepository.findByEmailIgnoreCase(registration.getEmail());
        findUserName = userRepository.findByUsernameIgnoreCase(registration.getUsername());
        if(findUserEmail.isPresent()
                || findUserName.isPresent() )
        {
            throw new UserException();
        }
        User user = new User();
        user.setEmail(registration.getEmail());
        user.setUsername(registration.getUsername());
        user.setFirstname(registration.getFirstname());
        user.setLastname(registration.getLastname());
        user.setPassword(encryptionService.encryptPassword(registration.getPassword()));
        VerficationToken verficationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verficationToken);

        return userRepository.save(user);
    }

    private VerficationToken createVerificationToken(User user){
        VerficationToken verficationToken = new VerficationToken();
        verficationToken.setToken(jwtService.generateVerificationJWT(user));
        verficationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verficationToken.setUser(user);
        user.getVerficationTokens().add(verficationToken);
        return  verficationToken;
    }

    public String loginUser(Login login) throws UserNotVerifiedException, EmailFailureException {
        findUserName = userRepository.findByUsernameIgnoreCase(login.getUsername());
        if (findUserName.isPresent()) {
            User user = findUserName.get();
            if (encryptionService.verifyPassword(login.getPassword(), user.getPassword()))
                if(user.isEmailVerfified()){
                return  jwtService.generateJWT(user);
            }
            else {
                List<VerficationToken> verficationTokens = user.getVerficationTokens();
                boolean resend = verficationTokens.size() == 0 ||
                        verficationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000))) ;
                if(resend){
                   VerficationToken verficationToken = createVerificationToken(user);
                    verificationTokenRepository.save(verficationToken);
                    emailService.sendVerificationEmail(verficationToken);
                }
                throw  new UserNotVerifiedException(resend);
            }
        }
        return null;
    }

    @Transactional //because we change data in the database
    public boolean verifyUser(String token){
        Optional<VerficationToken> opToken = verificationTokenRepository.findByToken(token);
        if(opToken.isPresent()){
            VerficationToken verficationToken = opToken.get();
            User user = verficationToken.getUser();
            if(!user.isEmailVerfified()){
                user.setEmailVerfified(true);
                userRepository.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

}

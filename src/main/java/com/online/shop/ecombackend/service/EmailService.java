package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.exception.EmailFailureException;
import com.online.shop.ecombackend.model.User;
import com.online.shop.ecombackend.model.VerficationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${email.from}")
    private String fromAddress;
    @Value("${app.frontend.url}")
    private String url;
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    public  void sendVerificationEmail(VerficationToken verficationToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verficationToken.getUser().getEmail());
        message.setSubject("Verify your email to activate your account.");
        message.setText("Please follow the link below to verify your email to activate your account.\r" +
               url + "/auth/verify?token=" + verficationToken.getToken());
        try {
            javaMailSender.send(message);
        }
        catch (MailException ex){

        throw new EmailFailureException();
        }
    }


    /**
     * Sends a password reset request email to the user.
     * @param user The user to send to.
     * @param token The token to send the user for reset.
     * @throws EmailFailureException
     */
    public void sendPasswordResetEmail(User user, String token) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your password reset request link.");
        message.setText("You requested a password reset on our website. Please " +
                "find the link below to be able to reset your password.\n" + url +
                "/auth/reset?token=" + token);
        try {
            javaMailSender.send(message);
            System.out.println("mail sent");
        } catch (MailException ex) {
            System.out.println("mail not sent");
            ex.printStackTrace();
            throw new EmailFailureException();
        }
    }

}

package com.builder.securedoc.service.impl;

import com.builder.securedoc.exception.ApiException;
import com.builder.securedoc.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.builder.securedoc.utils.EmailUtils.getEMailMessage;
import static com.builder.securedoc.utils.EmailUtils.getResetPasswordMessage;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String PASSWORD_RESET_REQUEST = "Reset Password Request";

    private final JavaMailSender sender;
    private final String formEmail;
    private final String host;

    public EmailServiceImpl(JavaMailSender sender,
                            @Value("${spring.mail.username}") String formEmail,
                            @Value("${spring.mail.verify.host}") String host) {
        this.sender = sender;
        this.formEmail = formEmail;
        this.host = host;
    }

    @Override
    @Async
    public void sendNewAccountEmail(String name, String email, String token) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(formEmail);
            message.setTo(email);
            message.setText(getEMailMessage(name, host, token));
            sender.send(message);
        } catch (Exception e) {
            log.error("Error sending new account email: {}", e.getMessage());
            throw new ApiException("Unable to send email.");
        }
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String name, String email, String token) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(PASSWORD_RESET_REQUEST);
            message.setFrom(formEmail);
            message.setTo(email);
            message.setText(getResetPasswordMessage(name, host, token));
            sender.send(message);
        } catch (Exception e) {
            log.error("Error sending password reset email: {}", e.getMessage());
            throw new ApiException("Unable to send email.");
        }
    }
}

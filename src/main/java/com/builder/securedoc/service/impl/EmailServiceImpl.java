package com.builder.securedoc.service.impl;

import com.builder.securedoc.exception.ApiException;
import com.builder.securedoc.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.builder.securedoc.utils.EmailUtils.getEMailMessage;
import static com.builder.securedoc.utils.EmailUtils.getResetPasswordMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String PASSWORD_RESET_REQUEST = "Reset Password Request";
    @Value(value = "${spring.mail.username}")
    private final JavaMailSender sender;
    @Value(value = "${spring.mail.verify.host}")
    private String host;
    private String formEmail;
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

            log.error(e.getMessage());
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

            log.error(e.getMessage());
            throw new ApiException("Unable to send email.");
        }
    }
}

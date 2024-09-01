package com.builder.securedoc.event.listener;

import com.builder.securedoc.event.UserEvent;
import com.builder.securedoc.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserEventListener {

    private final EmailService emailService;

    @EventListener
    public void onUserEven(UserEvent event) {

        switch (event.getType()) {
            case REGISTRATION -> emailService.sendNewAccountEmail(event.getUser().getFirstName(),
                    event.getUser().getEmail(),
                    (String) event.getData().get("key"));
            case RESETPASSWORD -> emailService.sendPasswordResetEmail(event.getUser().getFirstName(),
                    event.getUser().getEmail(),
                    (String) event.getData().get("key"));
            default -> {}
        }
    }
}

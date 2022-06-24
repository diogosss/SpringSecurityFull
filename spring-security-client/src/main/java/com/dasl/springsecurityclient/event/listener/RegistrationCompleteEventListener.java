package com.dasl.springsecurityclient.event.listener;

import com.dasl.springsecurityclient.entity.User;
import com.dasl.springsecurityclient.event.RegistrationCompleteEvent;
import com.dasl.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //crear el token de derificaion del ususario
        //con un link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        //enviar un email al user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        //send verificationEmail() //este es un emulador
        log.info("Click the link to verify your account: {}",url);
    }
}

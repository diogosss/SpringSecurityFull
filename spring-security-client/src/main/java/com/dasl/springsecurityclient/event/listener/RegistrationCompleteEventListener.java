package com.dasl.springsecurityclient.event.listener;

import com.dasl.springsecurityclient.entity.User;
import com.dasl.springsecurityclient.event.RegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //crear el token de derificaion del ususario
        //con un link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        //enviar un email al user
    }
}

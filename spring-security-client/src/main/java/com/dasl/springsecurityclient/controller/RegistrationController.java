package com.dasl.springsecurityclient.controller;

import com.dasl.springsecurityclient.entity.User;
import com.dasl.springsecurityclient.event.RegistrationCompleteEvent;
import com.dasl.springsecurityclient.model.UserModel;
import com.dasl.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                appicationUrl(request)
        ));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String veryfyRegistration(@RequestParam("token") String token){
        String result =  userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User Verifyes Successfully";
        }else {
            return "Bad User";
        }
    }

    private String appicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}

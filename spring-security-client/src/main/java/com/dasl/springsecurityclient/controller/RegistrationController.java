package com.dasl.springsecurityclient.controller;

import com.dasl.springsecurityclient.entity.User;
import com.dasl.springsecurityclient.entity.VerificationToken;
import com.dasl.springsecurityclient.event.RegistrationCompleteEvent;
import com.dasl.springsecurityclient.model.PasswordModel;
import com.dasl.springsecurityclient.model.UserModel;
import com.dasl.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
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
                applicationUrl(request)
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

    @GetMapping("/resendverifycationtoken")
    public String resendVerificationtoken(@RequestParam("token") String oldtoken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldtoken);
        User user = verificationToken.getUser();
        //send email here o usar el eventListner
        resendVerificationtokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Send";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {

        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url="";
        if(user!=null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordTokenForUser(user,token);
            url = passwordResetTokenMAil(user, applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if (user.isPresent()){
            userService.changePassord(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        }else{
            return "Invalid token";
        }


    }

    private String passwordResetTokenMAil(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;

        //send verificationEmail() //este es un emulador
        log.info("Click the link to Reset  your password: {}",url);
        return url;
    }

    private void resendVerificationtokenMail(User user, String applicationUrl, VerificationToken verificationToken ) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

        //send verificationEmail() //este es un emulador
        log.info("Click the link to verify your account: {}",url);

    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}

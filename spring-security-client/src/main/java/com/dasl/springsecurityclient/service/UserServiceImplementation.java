package com.dasl.springsecurityclient.service;

import com.dasl.springsecurityclient.entity.User;
import com.dasl.springsecurityclient.entity.VerificationToken;
import com.dasl.springsecurityclient.model.UserModel;
import com.dasl.springsecurityclient.repository.UserReposity;
import com.dasl.springsecurityclient.repository.VerificationTokenREpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserReposity userReposity;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenREpository verificationTokenREpository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userReposity.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenREpository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenREpository.findByToken(token);
        if(verificationToken == null){
            return "Invalid Token";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <=0)
        {
            verificationTokenREpository.delete(verificationToken);
            return "expired";
        }

        user.setEnable(true);
        userReposity.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldtoken) {
        VerificationToken verificationToken = verificationTokenREpository.findByToken(oldtoken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenREpository.save(verificationToken);
        return verificationToken;
    }
}

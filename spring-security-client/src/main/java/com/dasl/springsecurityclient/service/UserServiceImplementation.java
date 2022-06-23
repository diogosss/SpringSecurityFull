package com.dasl.springsecurityclient.service;

import com.dasl.springsecurityclient.entity.User;
import com.dasl.springsecurityclient.entity.VerificationToken;
import com.dasl.springsecurityclient.model.UserModel;
import com.dasl.springsecurityclient.repository.UserReposity;
import com.dasl.springsecurityclient.repository.VerificationTokenREpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
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
}

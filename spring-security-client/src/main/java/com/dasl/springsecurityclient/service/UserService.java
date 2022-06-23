package com.dasl.springsecurityclient.service;

import com.dasl.springsecurityclient.entity.User;
import com.dasl.springsecurityclient.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);
}

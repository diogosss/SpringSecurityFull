package com.dasl.springsecurityclient.model;

import lombok.Data;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;

@Data
public class PasswordModel {

    private String email;
    private String oldPassword;
    private String newPassword;
}

package com.dasl.springsecurityclient.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="users") //USer es alabra reservad aen postgresql
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    @Column(length = 60) //maxim 60 characters
    private String password;


    private String role;
    private boolean enable = false;




}

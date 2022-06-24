package com.dasl.Oauthauthorizationserver.repository;

import com.dasl.Oauthauthorizationserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposity extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

package com.dasl.springsecurityclient.repository;

import com.dasl.springsecurityclient.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenREpository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}

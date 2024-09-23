package com.sritel.accountservice.repository;

import com.sritel.accountservice.entity.User;
import com.sritel.accountservice.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}

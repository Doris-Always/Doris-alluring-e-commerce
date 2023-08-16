package com.example.ecommerce.repository;

import com.example.ecommerce.model.ConfirmationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    void deleteTokenByExpiredAtBefore(LocalDateTime currentTime);

    @Transactional
    @Modifying
    @Query(" UPDATE ConfirmationToken confirmationToken " +
            " SET confirmationToken.confirmAt = ?1"  +
            " WHERE confirmationToken.token = ?2 ")
    void setExpiredAt(LocalDateTime now, String token);
}

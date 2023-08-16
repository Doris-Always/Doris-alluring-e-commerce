package com.example.ecommerce.service;

import com.example.ecommerce.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getConfirmationToken(String token);
    void deleteConfirmationToken();
    void setExpiredAt(String token);

}

package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.model.ConfirmationToken;
import com.example.ecommerce.repository.ConfirmationTokenRepository;
import com.example.ecommerce.service.ConfirmTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmTokenService {

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> getConfirmationToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void deleteConfirmationToken() {
        confirmationTokenRepository.deleteTokenByExpiredAtBefore(LocalDateTime.now());

    }

    @Override
    public void setExpiredAt(String token) {
        confirmationTokenRepository.setExpiredAt(LocalDateTime.now(),token);
    }

}

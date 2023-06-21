package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ResetPasswordReq;
import com.example.ecommerce.exception.PasswordMismatchException;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    @Autowired
    UserRepository userRepository;


    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordReq resetPasswordReq) {
        User foundCustomer = userRepository.findUserByEmail(resetPasswordReq.getEmail()).orElseThrow(()->new UserNotFoundException("customer does not exist"));
        if (!Objects.equals(resetPasswordReq.getNewPassword(), resetPasswordReq.getConfirmPassword())) throw new PasswordMismatchException("Password is not a match");
        foundCustomer.setPassword(resetPasswordReq.getNewPassword());
        userRepository.save(foundCustomer);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
}

package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import com.example.ecommerce.exception.PasswordMismatchException;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

    @Autowired
    UserRepository customerRepository;

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordReq changePasswordReq) {
        User foundCustomer = customerRepository.findUserByEmail(changePasswordReq.getEmail()).orElseThrow(()->new UserNotFoundException("user does not exist"));
        if (!Objects.equals(foundCustomer.getPassword(), changePasswordReq.getOldPassword())) throw new PasswordMismatchException("Password does not match");
        if (!Objects.equals(changePasswordReq.getNewPassword(), changePasswordReq.getConfirmPassword())) throw new PasswordMismatchException("Password did not match");
        foundCustomer.setPassword(changePasswordReq.getNewPassword());
        customerRepository.save(foundCustomer);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);

    }
}

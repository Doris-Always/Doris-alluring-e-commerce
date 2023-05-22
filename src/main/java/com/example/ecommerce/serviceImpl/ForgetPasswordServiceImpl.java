package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ResetPasswordReq;
import com.example.ecommerce.exception.PasswordMismatchException;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    @Autowired
    CustomerRepository customerRepository;


    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordReq resetPasswordReq) {
        Customer foundCustomer = customerRepository.findUserByEmail(resetPasswordReq.getEmail());
        if (foundCustomer == null) throw new UserNotFoundException("Customer does not exist");
        if (!Objects.equals(resetPasswordReq.getNewPassword(), resetPasswordReq.getConfirmPassword())) throw new PasswordMismatchException("Password is not a match");
        foundCustomer.setPassword(resetPasswordReq.getNewPassword());
        customerRepository.save(foundCustomer);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
}

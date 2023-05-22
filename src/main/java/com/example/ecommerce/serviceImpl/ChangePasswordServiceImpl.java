package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import com.example.ecommerce.exception.PasswordMismatchException;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordReq changePasswordReq) {
        Customer foundCustomer = customerRepository.findUserByEmail(changePasswordReq.getEmail());
        if (foundCustomer == null) throw new UserNotFoundException("Customer does not exist");
        if (!Objects.equals(foundCustomer.getPassword(), changePasswordReq.getOldPassword())) throw new PasswordMismatchException("Password does not match");
        if (!Objects.equals(changePasswordReq.getNewPassword(), changePasswordReq.getConfirmPassword())) throw new PasswordMismatchException("Password did not match");
        foundCustomer.setPassword(changePasswordReq.getNewPassword());
        customerRepository.save(foundCustomer);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);

    }
}

package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ResetLinkReq;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.SendPasswordResetLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendPasswordResetLinkImpl implements SendPasswordResetLink {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Override
    public ResponseEntity<?> sendLink(ResetLinkReq resetLinkReq) {
        User foundCustomer = userRepository.findUserByEmail(resetLinkReq.getEmail()).orElseThrow(()->new UserNotFoundException("customer does not exist"));
        String url = "my reset password link ";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("okoloebelechukwu93@gmail.com");
        simpleMailMessage.setTo(foundCustomer.getEmail());
        simpleMailMessage.setSubject("Password Reset link");
        simpleMailMessage.setText("follow this link to reset your password: " + url);
        javaMailSender.send(simpleMailMessage);
        return new ResponseEntity<>("Password reset link sent successfully", HttpStatus.OK);
    }
}

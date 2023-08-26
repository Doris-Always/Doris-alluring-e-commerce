package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.EmailRequest;
import com.example.ecommerce.dto.request.ForgotPasswordRequest;
import com.example.ecommerce.dto.request.OtpRequest;
import com.example.ecommerce.dto.request.ResetPasswordReq;
import com.example.ecommerce.dto.response.ForgotPasswordResponse;
import com.example.ecommerce.exception.PasswordMismatchException;
import com.example.ecommerce.exception.TokenNotFoundException;
import com.example.ecommerce.model.ConfirmationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ConfirmTokenService;
import com.example.ecommerce.service.EmailService;
import com.example.ecommerce.service.ForgetPasswordService;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import static com.example.ecommerce.utils.EmailUtils.buildForgotPasswordEmail;
import static com.example.ecommerce.utils.EmailUtils.generateOTP;

    @Service
    @RequiredArgsConstructor
    public class ForgetPasswordServiceImpl implements ForgetPasswordService {
        @Autowired
        UserRepository userRepository;
        @Autowired
        UserService userService;
        @Autowired
        ConfirmTokenService confirmTokenService;
        @Autowired
        EmailService emailService;

        private final PasswordEncoder passwordEncoder;


        @Override
        public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
            User foundUser = userService.findUserByEmail(forgotPasswordRequest.getEmail());
            String token = generateOTP();
            ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(5),foundUser);
            confirmTokenService.saveConfirmationToken(confirmationToken);
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setMessage(buildForgotPasswordEmail(foundUser.getLastName(),token));
            emailRequest.setReceiver(foundUser.getEmail());
            emailService.sendOTP(emailRequest);
            return ForgotPasswordResponse.builder()
                    .token(token)
                    .build();


        }


        @Override
        public ResponseEntity<?> resetPassword(ResetPasswordReq resetPasswordReq) {
            User foundCustomer = userService.findUserByEmail(resetPasswordReq.getEmail());
           ConfirmationToken foundToken = confirmTokenService.getConfirmationToken(resetPasswordReq.getToken())
                   .orElseThrow(()-> new TokenNotFoundException("token not found"));

            if (foundToken.getExpiredAt().isBefore(LocalDateTime.now())) throw new IllegalStateException("token is expired");

            if (foundToken.getConfirmAt() != null) throw new IllegalStateException("token has already been confirmed");

            if (!Objects.equals(resetPasswordReq.getNewPassword(), resetPasswordReq.getConfirmPassword())) throw new PasswordMismatchException("Password is not a match");
            foundCustomer.setPassword(passwordEncoder.encode(resetPasswordReq.getNewPassword()));
            userRepository.save(foundCustomer);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        }
    }



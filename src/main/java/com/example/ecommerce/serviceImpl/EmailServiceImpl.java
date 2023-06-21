package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${username}")
    private String sender;
    String subject = "OTP Verification Email";

//    public EmailServiceImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    private String generateOTP(){
        StringBuilder generatedOtp = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        int otp =100000 + secureRandom.nextInt(900000);
        generatedOtp.append(otp);
        return generatedOtp.toString();

    }

    @Override
    public void sendOTP(String receiver){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("okoloebelechukwu93@gmail.com");
                simpleMailMessage.setTo(receiver);
                simpleMailMessage.setSubject("One Time Password from Doris Alluring stores");
                simpleMailMessage.setText("your one time password is: " + generateOTP() + "please secure it");
                javaMailSender.send(simpleMailMessage);

    }

    @Override
    public void sendPaymentConfirmationEmail(String receiver) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("okoloebelechukwu93@gmail.com");
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject("Payment Verification From Doris Alluring");
        simpleMailMessage.setText("your payment to Doris Alluring stores is successful,you will get your order delivered soon");
        javaMailSender.send(simpleMailMessage);
    }
}

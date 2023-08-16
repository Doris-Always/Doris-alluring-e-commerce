package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.OtpRequest;
import com.example.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Async
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${username}")
    private String sender;
    String subject = "OTP Verification Email";

//    public EmailServiceImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }



    @Override
    public void sendOTP(String receiver,String message) {
        try{
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "Utf-8");
            mimeMessageHelper.setSubject("Email Verification One Time Password");
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setFrom("okoloebelechukwu93@gmail.com");
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e){
            log.info("Problem 1: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (MailException e){
            log.info("Problem 2" + e.getMessage());
            throw new RuntimeException(e);
        }
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//                simpleMailMessage.setFrom("okoloebelechukwu93@gmail.com");
//                simpleMailMessage.setTo(receiver);
//                simpleMailMessage.setSubject("One Time Password from Doris Alluring stores");
//                simpleMailMessage.setText("your one time password is: " + generateOTP() + "please secure it");
//                javaMailSender.send(simpleMailMessage);

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

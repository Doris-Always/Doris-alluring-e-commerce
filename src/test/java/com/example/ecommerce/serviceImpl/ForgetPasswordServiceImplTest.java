package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ResetPasswordReq;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ForgetPasswordServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    ForgetPasswordServiceImpl forgetPasswordService;

    @BeforeEach
    void setUp() {
    }
    @Test
    void testForgetPassword(){
        ResetPasswordReq resetPasswordReq = new ResetPasswordReq();
        resetPasswordReq.setEmail("dorisebele47@gmail.com");
        resetPasswordReq.setNewPassword("12345");
        resetPasswordReq.setConfirmPassword("12345");
        User user = new User();
        user.setEmail("dorisebele47@gmail.com");
        user.setPassword("12345");
        when(userRepository.findUserByEmail(resetPasswordReq.getEmail())).thenReturn(user);
        ResponseEntity<?> resetResponse =
                forgetPasswordService.resetPassword(resetPasswordReq);
        assertNotNull(user);
        assertEquals(HttpStatus.OK,resetResponse.getStatusCode());
        assertEquals(resetPasswordReq.getNewPassword(),user.getPassword());

    }
}
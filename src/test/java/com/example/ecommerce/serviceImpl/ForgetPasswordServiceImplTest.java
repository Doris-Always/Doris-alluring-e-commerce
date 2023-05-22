package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import com.example.ecommerce.dto.request.ResetPasswordReq;
import com.example.ecommerce.dto.response.ApiResponse;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.ForgetPasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    CustomerRepository customerRepository;

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
        Customer customer = new Customer();
        customer.setEmail("dorisebele47@gmail.com");
        customer.setPassword("12345");
        when(customerRepository.findUserByEmail(resetPasswordReq.getEmail())).thenReturn(customer);
        ResponseEntity<?> resetResponse =
                forgetPasswordService.resetPassword(resetPasswordReq);
        assertNotNull(customer);
        assertEquals(HttpStatus.OK,resetResponse.getStatusCode());
        assertEquals(resetPasswordReq.getNewPassword(),customer.getPassword());

    }
}
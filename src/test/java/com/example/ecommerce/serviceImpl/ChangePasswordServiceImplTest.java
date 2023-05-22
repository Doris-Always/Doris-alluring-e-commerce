package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ChangePasswordServiceImplTest {
    @InjectMocks
    ChangePasswordServiceImpl changePasswordService;
    @Mock
    CustomerRepository customerRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void test_changePassword(){
        ChangePasswordReq changePasswordReq = new ChangePasswordReq();
        changePasswordReq.setEmail("doris@gmail.com");
        changePasswordReq.setOldPassword("23457");
        changePasswordReq.setNewPassword("98562");
        changePasswordReq.setConfirmPassword("98562");
        Customer foundCustomer = new Customer();
        foundCustomer.setEmail("doris@gmail.com");
        foundCustomer.setPassword("23457");

        when(customerRepository.findUserByEmail(changePasswordReq.getEmail())).thenReturn(foundCustomer);
        when(customerRepository.save(foundCustomer)).thenReturn(foundCustomer);
        changePasswordService.changePassword(changePasswordReq);
         foundCustomer = customerRepository.findUserByEmail(changePasswordReq.getEmail());
        assertEquals(changePasswordReq.getNewPassword(),foundCustomer.getPassword());


    }
}
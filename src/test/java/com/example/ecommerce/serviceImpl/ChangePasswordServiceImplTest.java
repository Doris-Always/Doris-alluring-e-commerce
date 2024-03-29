package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ChangePasswordServiceImplTest {
    @InjectMocks
    ChangePasswordServiceImpl changePasswordService;
    @Mock
    UserRepository userRepository;
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

        User customer = new User();
        customer.setEmail("doris@gmail.com");
        customer.setPassword("23457");

        when(userRepository.save(customer)).thenReturn(customer);
        when(userRepository.findUserByEmail(changePasswordReq.getEmail())).thenReturn(Optional.of(customer));

        changePasswordService.changePassword(changePasswordReq);
         var foundCustomer = userRepository.findUserByEmail(changePasswordReq.getEmail());
        assertEquals(changePasswordReq.getNewPassword(),foundCustomer.get().getPassword());


    }
}
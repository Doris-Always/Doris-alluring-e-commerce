package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import com.example.ecommerce.dto.request.LoginRequest;
import com.example.ecommerce.dto.request.UserRegisterRequest;
import com.example.ecommerce.dto.response.ApiResponse;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest

@RunWith(MockitoJUnitRunner.class)
class CustomerServiceImplTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;
//    @Autowired
//    CustomerService customerService;


    UserRegisterRequest customerRequest;
    LoginRequest loginRequest;

    @BeforeEach

    void setUp() {
        MockitoAnnotations.initMocks(this);
//        customerService = new CustomerServiceImpl();
        customerRequest = new UserRegisterRequest();
        customerRequest.setFirstName("Neche");
        customerRequest.setLastName("Okolo");
        customerRequest.setEmail("dorisebele47@gmail.com");
        customerRequest.setPassword("eb0ko76");

    }
    @Test
    void testThatCustomerCanRegister(){
        customerService.register(customerRequest);
        assertEquals(2,customerService.count());
    }

    @Test
    void testThatAUserCanLogin(){
        loginRequest = new LoginRequest();
        loginRequest.setEmail("dorisebele47@gmail.com");
        loginRequest.setPassword("567890");
       LoginResponse successResponse = customerService.login(loginRequest);
        assertEquals(successResponse,new LoginResponse("login successful", HttpStatus.OK, LocalDateTime.now()));
    }
    //Using MockTest
    @Test
    void testLogin(){
        Customer customer = new Customer();
        String email = "dorisebele47@gmail.com";
        String password = "567890";
        customer.setEmail(email);
        customer.setPassword(password);
        when(customerRepository.findUserByEmail(email)).thenReturn(customer);
        loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        LoginResponse loggedUser = customerService.login(loginRequest);
        assertNotNull(loggedUser);
        assertEquals("login successful",loggedUser.getMessage());

    }

    @Test
    void test_add_product_to_cart(){
        customerService.register(customerRequest);


    }

//    @Test
//    void testResetPassword(){
//        Customer customer = new Customer();
//        ResetPasswordRequest passwordRequest = new ResetPasswordRequest();
//        String email = "dorisebele47@gmail.com";
//        String newPassword = "12345";
//        customer.setEmail(email);
//        customer.setPassword("567890");
//
//     when(customerRepository.findUserByEmail(email)).thenReturn(customer);
//        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
//        passwordRequest.setEmail(email);
//        passwordRequest.setNewPassword(newPassword);
//        Customer updatedCustomer = customerService.resetPassword(passwordRequest);
//        assertNotNull(updatedCustomer);
//        assertEquals(email,updatedCustomer.getEmail());
//        assertEquals(newPassword,updatedCustomer.getPassword());

    }



package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.LoginRequest;
import com.example.ecommerce.dto.request.UpdateUserRequest;
import com.example.ecommerce.dto.request.UserRegisterRequest;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest

@RunWith(MockitoJUnitRunner.class)
class CustomerServiceImplTest {
    @Mock
    UserRepository customerRepository;

    @InjectMocks
    UserServiceImpl userService;
//    @Autowired
//    CustomerService customerService;
    @Mock
    EmailServiceImpl emailServiceImpl;
    @Captor
            private  ArgumentCaptor<User> userArgumentCaptor;

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
        userService.register(customerRequest);
        assertEquals(2,userService.count());
    }

    @Test
    void testThatAUserCanLogin(){
        loginRequest = new LoginRequest();
        loginRequest.setEmail("dorisebele47@gmail.com");
        loginRequest.setPassword("567890");
       LoginResponse successResponse = userService.login(loginRequest);
        assertEquals(successResponse,new LoginResponse("login successful", HttpStatus.OK, LocalDateTime.now()));
    }
    //Using MockTest
    @Test
    void testLogin(){
        User user = new User();
        String email = "dorisebele47@gmail.com";
        String password = "567890";
        user.setEmail(email);
        user.setPassword(password);
        when(customerRepository.findUserByEmail(email)).thenReturn(user);
        loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        LoginResponse loggedUser = userService.login(loginRequest);
        assertNotNull(loggedUser);
        assertEquals("login successful",loggedUser.getMessage());

    }

    @Test
    void test_that_i_can_get_all_users(){
        BDDMockito.when(customerRepository.save(userArgumentCaptor.capture())).thenReturn(new User());
        BDDMockito.when(customerRepository.findAll()).thenReturn(List.of(new User()));
        doNothing().when(emailServiceImpl).sendOTP(anyString());
        userService.register(customerRequest);
        BDDMockito.then(customerRepository).should().save(any(User.class));
        var allUsers = userService.findAllUser();
        assertEquals(1,allUsers.size());
    }
    @Test
    void test_that_i_can_get_user_by_firstname(){
        User foundUser = new User();
        foundUser.setFirstName("Doris");
        BDDMockito.when(customerRepository.findUserByFirstName(anyString())).thenReturn(foundUser);
        String firstName = "Doris";
        var user = userService.findUserByFirstName(firstName);
        assertEquals("Doris",user.getFirstName());
    }
    @Test
    void test_that_user_details_can_be_updated(){
        BDDMockito.when(customerRepository.save(userArgumentCaptor.capture())).thenReturn(new User());
        doNothing().when(emailServiceImpl).sendOTP(anyString());
       User prevUserInfo = userService.register(customerRequest);
        BDDMockito.then(customerRepository).should().save(any(User.class));
        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setFirstName("Emeka");
        updateRequest.setLastName("Abee");
        updateRequest.setEmail("abee@gmail.com");
        User updatedUser = userService.updateUserInfo(prevUserInfo.getId(),updateRequest);
        assertEquals(updatedUser.getFirstName(),prevUserInfo.getFirstName());
    }




    }



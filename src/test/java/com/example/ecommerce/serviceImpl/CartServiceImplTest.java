package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddToCartRequest;
import com.example.ecommerce.dto.request.UpdateCartProductReq;
import com.example.ecommerce.dto.request.UserRegisterRequest;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartProductService;
import com.example.ecommerce.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ExtendWith(MockitoExtension.class)

class CartServiceImplTest {
   @Autowired
    CartRepository cartRepository;
    @Autowired

    UserRepository userRepository;
    @Autowired
    CartService cartService;
    @Autowired
    EmailServiceImpl emailService;
    @InjectMocks

    @Autowired
    UserServiceImpl userService;

    @Mock
    EmailServiceImpl emailServiceImpl;
    @Autowired
    CartProductService cartProductService;
        UserRegisterRequest registerRequest;

        @BeforeEach
        void setUp(){
            registerRequest = create();
        }
        private UserRegisterRequest create(){
            registerRequest = new UserRegisterRequest();
            registerRequest.setFirstName("Neche");
            registerRequest.setLastName("Okolo");
            registerRequest.setPassword("eb0ko76");
            registerRequest.setEmail("dorisebele47@gmail.com");
            registerRequest.setPhoneNumber("09076543211");

            return  registerRequest;
        }

        @Test
        void test_registered_customer_can_add_product_to_cart() {
            doNothing().when(emailServiceImpl).sendOTP(anyString());
            User customer = userService.register(registerRequest);
            assertEquals(0, customer.getCart().getCartProducts().size());

            AddToCartRequest addToCartRequest = new AddToCartRequest();
            addToCartRequest.setProductName("tea");
            addToCartRequest.setQuantity(2);

            userService.addToCart(1L, addToCartRequest);

            Optional<User> foundCustomer = userRepository.findById(1L);
            assertEquals(1, foundCustomer.get().getCart().getCartProducts().size());


    }


}
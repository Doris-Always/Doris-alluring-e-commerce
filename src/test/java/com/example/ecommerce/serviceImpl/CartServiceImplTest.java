package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.AddToCartRequest;
import com.example.ecommerce.dto.request.UpdateCartProductReq;
import com.example.ecommerce.dto.request.UserRegisterRequest;
import com.example.ecommerce.dto.response.RegistrationResponse;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.ConfirmationToken;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ConfirmationTokenRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
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
    @Autowired
    ConfirmationTokenRepository  confirmationTokenRepository;
    @Mock
    EmailServiceImpl emailServiceImpl;

    @Mock
    ProductServiceImpl productService;
    @Autowired
    CartProductService cartProductService;
        UserRegisterRequest registerRequest;
        @BeforeEach
        void setUp(){
            registerRequest = create();
        }
        private void deleteOtherUsers(){
            List<User> users =userRepository.findAll();
            List<ConfirmationToken> confirmationTokens = confirmationTokenRepository.findAll();


            for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId() > 2) {
//                            confirmationTokenRepository.delete(confirmationTokens.get(1));
                        userRepository.delete(users.get(i));
                    }
//                System.out.println(users.get(i));

            }
        }
        private UserRegisterRequest create(){
          UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
            userRegisterRequest.setFirstName("Ben");
            userRegisterRequest.setLastName("Billion");
            userRegisterRequest.setPassword("Rty2345");
            userRegisterRequest.setEmail("ebele1@gmail.com");
            userRegisterRequest.setPhoneNumber("09075678903");

            return userRegisterRequest;
        }

        @Test
        void test_registered_customer_can_add_product_to_cart() {
            doNothing().when(emailServiceImpl).sendOTP(anyString(),anyString());
            deleteOtherUsers();
            RegistrationResponse response = userService.register(create());
            assertEquals(response.getMessage(),"token sent to your email");

            var id = response.getId();

            User customer = userService.findUserById(id);

            assertEquals(0, customer.getCart().getCartProducts().size());

            AddToCartRequest addToCartRequest = new AddToCartRequest();
            addToCartRequest.setProductName("aloe lightning");
            addToCartRequest.setQuantity(2);
            addToCartRequest.setProductId(1L);
            addToCartRequest.setCartId(customer.getId());

            userService.addToCart(customer.getId(), addToCartRequest);

            Optional<User> foundCustomer = userRepository.findById(customer.getId());
            assertEquals(1, foundCustomer.get().getCart().getCartProducts().size());


    }


}
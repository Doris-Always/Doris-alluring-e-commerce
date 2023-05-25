package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.AddToCartRequest;
import com.example.ecommerce.dto.request.UserRegisterRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartProductRepository;
import com.example.ecommerce.service.CartProductService;
import com.example.ecommerce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CartProductServiceImplTest {

    @Autowired
    CartProductService cartProductService;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    UserService userService;
    UserRegisterRequest registerRequest;
    @BeforeEach
    void setUp() {
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

//    @Test
//    void test_that_product_can_be_removed_from_cart(){
//       User customer = userService.register(registerRequest);
//       var foundUsers = userService.findAllUser();
//        assertEquals(1,foundUsers.size());
//    }

}
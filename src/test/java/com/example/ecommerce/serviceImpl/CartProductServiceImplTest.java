package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.AddToCartRequest;
import com.example.ecommerce.dto.request.UpdateCartProductReq;
import com.example.ecommerce.dto.request.UserRegisterRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartProductRepository;
import com.example.ecommerce.service.CartProductService;
import com.example.ecommerce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartProductServiceImplTest {

    @InjectMocks
    CartProductServiceImpl cartProductService;

    @Mock
    CartProductRepository cartProductRepository;

    @Autowired
    UserService userService;
    @Captor
    private ArgumentCaptor<CartProduct> cartProductArgumentCaptor;
    UserRegisterRequest registerRequest;
    CartProduct cartProduct;

    @BeforeEach
    void setUp() {
        registerRequest = create();
        cartProduct = new CartProduct();
        Cart cart = new Cart();
        cartProduct.setCart(cart);
        cartProduct.setProductId(1L);
        cartProduct.setProductName("baby face");
        cartProduct.setCategory(Category.FACE_CLEANSER);
        cartProduct.setPricePerUnit(new BigDecimal(20));

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
    void test_update_cart_product(){
        when(cartProductRepository.save(cartProduct)).thenReturn(new CartProduct());
        CartProduct savedProduct = cartProductService.addCartProduct(cartProduct);
        UpdateCartProductReq updateCartProductReq = new UpdateCartProductReq();
        updateCartProductReq.setCartProductId(savedProduct.getProductId());
        updateCartProductReq.setQuantity(1);
        var updatedCartProduct =cartProductService.updateCartProduct(updateCartProductReq);
        var foundProduct = cartProductService.findProductById(savedProduct.getId());
        assertEquals(1,foundProduct.getQuantity());



    }

}
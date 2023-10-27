package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.RatingRequest;
import com.example.ecommerce.dto.request.UserRegisterRequest;

import com.example.ecommerce.dto.response.RatingResponse;
import com.example.ecommerce.dto.response.RegistrationResponse;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Rating;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReviewsAndRatingsTest {

    @Autowired
    UserRepository customerRepository;

    @Autowired
    UserServiceImpl userService;

    UserRegisterRequest customerRequest;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RatingService ratingService;
    @Autowired
    ProductService productService;

    AddProductRequest addProductRequest;

    Product firstProduct;
    Product secondProduct;
    RegistrationResponse registrationResponse;

    @BeforeEach
    public void beforeEach(){
        registerUser();
        addProduct();
    }
@Test
public void RateProduct(){
         RatingRequest ratingRequest = new RatingRequest();
         ratingRequest.setProductId(firstProduct.getId());
         ratingRequest.setRating(3);
         ratingRequest.setUserId(registrationResponse.getId());

         RatingResponse ratingResponse = ratingService.rateProduct(ratingRequest);
          assertEquals("Product rated successfully", ratingResponse.getMessage());
          Rating rating = ratingService.findRatingById(ratingResponse.getRatingId());

          assertEquals(ratingRequest.getRating(), rating.getRating());
//        Product foundProduct = ratingService.findProductById(firstProduct.getId());


}
    public void registerUser(){

        customerRequest = new UserRegisterRequest();
        customerRequest.setFirstName("ned");
        customerRequest.setLastName("stark");
        customerRequest.setEmail("dorisebele47@gmail.com");
        customerRequest.setPassword("NedStark1");

       registrationResponse = userService.register(customerRequest);
    }
    public void addProduct(){
        addProductRequest = new AddProductRequest();
        BigDecimal productPrice = new BigDecimal("400.00");
        addProductRequest.setProductName("aloe ointment");
        addProductRequest.setQuantity(5);
        addProductRequest.setPricePerUnit(productPrice);
        addProductRequest.setCategory(Category.ORGANIC_OIL);
        firstProduct = productService.addProduct(addProductRequest);

        addProductRequest = new AddProductRequest();
        productPrice = new BigDecimal("200.00");
        addProductRequest.setProductName("brown skin face Cleanser");
        addProductRequest.setQuantity(3);
        addProductRequest.setPricePerUnit(productPrice);
        addProductRequest.setCategory(Category.FACE_CLEANSER);
        secondProduct = productService.addProduct(addProductRequest);
    }
}

package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
   @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductServiceImpl productService;

    AddProductRequest addProductRequest;
    AddProductRequest addProductRequest2;

    @BeforeEach
    void setUp(){
        addProductRequest = new AddProductRequest();
        BigDecimal bigDecimal = new BigDecimal("200.00");
        addProductRequest.setProductName("aloe lightning");
        addProductRequest.setQuantity(4);
        addProductRequest.setPricePerUnit(bigDecimal);
        addProductRequest.setCategory(Category.ORGANIC_CREAM);
        addProductRequest2 = new AddProductRequest();
        BigDecimal bigDecimal2 = new BigDecimal("300.00");
        addProductRequest2.setProductName("brown skin oil");
        addProductRequest2.setQuantity(2);
        addProductRequest2.setPricePerUnit(bigDecimal2);
        addProductRequest2.setCategory(Category.ORGANIC_OIL);
    }


    @Test
    void test_that_product_can_be_saved(){
        productService.addProduct(addProductRequest);
        Product savedProduct = productService.findProductById(1L);
        assertNotNull(savedProduct);

    }
    @Test
    void test_find_product_by_id(){
        productService.addProduct(addProductRequest);
        Product savedProduct = productService.findProductById(1L);
       Long productId = savedProduct.getId();
        assertEquals(1L,productId);
    }
    @Test
    void test_find_all_product(){
        productService.addProduct(addProductRequest);
        productService.addProduct(addProductRequest2);
        var allProduct =
                productService.findAllProduct();
        assertEquals(2,allProduct.size());

    }
    @Test
    void test_update_product(){
        AddProductRequest addProductRequest = new AddProductRequest();
        BigDecimal bigDecimal = new BigDecimal("400.00");
        addProductRequest.setProductName("Face oil");
        addProductRequest.setQuantity(5);
        addProductRequest.setPricePerUnit(bigDecimal);
        addProductRequest.setCategory(Category.ORGANIC_OIL);

      Product product =  productService.addProduct(addProductRequest);
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setProductName("aloe alluring");
        updateProductRequest.setQuantity(6);
        updateProductRequest.setPricePerUnit(new BigDecimal(300));
        updateProductRequest.setCategory(Category.BODY_SCRUB);
        Product updatedProduct = productService.updateProduct(product.getId(),updateProductRequest);
        assertEquals(product.getId(),updatedProduct.getId());
        assertNotEquals(product.getProductName(),updatedProduct.getProductName());

    }

}
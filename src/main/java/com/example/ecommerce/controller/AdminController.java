package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping("/findAllCustomer")
    public ResponseEntity<?> findAllCustomer() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/findCustomer/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long id){
        try {
             return new ResponseEntity<>(userService.findUserById(id),HttpStatus.OK);
        }catch ( UserNotFoundException userNotFoundException){
            return  new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest addProductRequest){
        return new ResponseEntity<>(productService.addProduct(addProductRequest), HttpStatus.OK);
    }
    @GetMapping("/allProduct")
    public ResponseEntity<?> getAllProduct(){
        return new ResponseEntity<>(productService.findAllProduct(),HttpStatus.OK);
    }
    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        return new ResponseEntity<>(productService.findProductById(id),HttpStatus.OK);
    }
    @PostMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody UpdateProductRequest updateProductRequest){
        return new ResponseEntity<>(productService.updateProduct(id,updateProductRequest),HttpStatus.OK);
    }
    @DeleteMapping("/removeProduct/{id}")
    public void removeProduct(@PathVariable("id") Long id){
        productService.removeProductById(id);
    }

}

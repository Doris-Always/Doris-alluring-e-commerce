package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.exception.UserAlreadyExistException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    CartProductServiceImpl cartProductService;


    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartService cartService;

    ModelMapper mapper = new ModelMapper();

    @Override
    public Customer register(UserRegisterRequest registerRequest) {
        Optional<Customer> foundCustomer = Optional.ofNullable(customerRepository.findUserByEmail(registerRequest.getEmail()));
        if (foundCustomer.isPresent()){
            throw new UserAlreadyExistException("User Already Exist");
        }
        String hashedPassword = PasswordEncoder.hashPassword(registerRequest.getPassword());
        Customer customer = mapper.map(registerRequest,Customer.class);
        customer.setPassword(hashedPassword);
        Cart cart = new Cart();
        customer.setCart(cart);
        emailService.sendOTP(customer.getEmail());
        customerRepository.save(customer);
        return customer;


    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Customer foundCustomer = customerRepository.findUserByEmail(loginRequest.getEmail());

        if (foundCustomer != null && PasswordEncoder.checkPwd(loginRequest.getPassword(),foundCustomer.getPassword())){
            return new LoginResponse("login successful",HttpStatus.OK,LocalDateTime.now());

        }
        return new LoginResponse("login failed, incorrect password", HttpStatus.UNAUTHORIZED,LocalDateTime.now());

    }

    @Override
    public Long count() {
        return customerRepository.count();
    }
    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();

    }

    @Override
    public Customer  findCustomerById(Long customerId) {
        var customer = customerRepository.findById(customerId);
        return customer.get();
    }


    @Override
    public String addToCart(Long customerId, AddToCartRequest cartRequest) {
        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()-> new IllegalArgumentException("Customer not found"));
          CartProduct cartProduct = new CartProduct();
          cartProduct.setProductName(cartRequest.getProductName());
          cartProduct.setQuantity(cartRequest.getQuantity());
          CartProduct savedCartProduct = cartProductService.addCartProduct(cartProduct);
          foundCustomer.getCart().getCartProducts().add(savedCartProduct);
          customerRepository.save(foundCustomer);
           return "added successfully";

        }




    public void removeItemFromCart(Long customerId,Long productId) {
            Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
            var cartProducts = foundCustomer.getCart().getCartProducts();
            for (int i = 0; i < cartProducts.size(); i++) {
                if (cartProducts.get(i).getId().equals(productId)) {
                    cartProducts.remove(i);
                }
            }
            customerRepository.save(foundCustomer);


        }
}




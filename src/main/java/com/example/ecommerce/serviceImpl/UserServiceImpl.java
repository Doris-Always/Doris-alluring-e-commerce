package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.exception.UserAlreadyExistException;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.CartProductRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartProductService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    CartProductServiceImpl cartProductService;


    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartService cartService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public User register(UserRegisterRequest registerRequest) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findUserByEmail(registerRequest.getEmail()));
        if (foundUser.isPresent()){
            throw new UserAlreadyExistException("User Already Exist");
        }
        String hashedPassword = PasswordEncoder.hashPassword(registerRequest.getPassword());
        User user = mapper.map(registerRequest,User.class);
        user.setPassword(hashedPassword);
        Cart cart = new Cart();
        user.setCart(cart);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.USER);
        userRoles.add(Role.CUSTOMER);
        user.setUserRoles(userRoles);
        emailService.sendOTP(user.getEmail());
        userRepository.save(user);
        return user;


    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User foundUser = userRepository.findUserByEmail(loginRequest.getEmail());

        if (foundUser != null && PasswordEncoder.checkPwd(loginRequest.getPassword(), foundUser.getPassword())){
            return new LoginResponse("login successful",HttpStatus.OK,LocalDateTime.now());

        }
        return new LoginResponse("login failed, incorrect password", HttpStatus.UNAUTHORIZED,LocalDateTime.now());

    }

    @Override
    public Long count() {
        return userRepository.count();
    }
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();


    }


    @Override
    public User findUserById(Long userId) {
        var user = userRepository.findById(userId);
        return user.get();
    }

    @Override
    public User findUserByFirstName(String firstName) {
        return userRepository.findUserByFirstName(firstName);
    }

    @Override
    public User updateUserInfo(Long userId,UpdateUserRequest updateUserRequest) {
        User foundUser = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("user not found"));
        foundUser.setFirstName(updateUserRequest.getFirstName());
        foundUser.setLastName(updateUserRequest.getLastName());
        foundUser.setEmail(updateUserRequest.getEmail());
        foundUser = userRepository.save(foundUser);
        return foundUser;
    }


    @Override
    public String addToCart(Long userId, AddToCartRequest cartRequest) {
        User foundUser = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("Customer not found"));
        Product product = productRepository.findProductById(cartRequest.getProductId());
          CartProduct cartProduct = new CartProduct();
          cartProduct.setProductName(cartRequest.getProductName());
          cartProduct.setQuantity(cartRequest.getQuantity());
          cartProduct.setProductId(product.getId());
          cartProduct.setPricePerUnit(product.getPricePerUnit());
          cartProduct.setCategory(product.getCategory());
          cartProduct.setCart(foundUser.getCart());
          cartProduct.setTotalPrice(calculateTotalPrice(cartRequest.getQuantity(),product.getPricePerUnit()));
          CartProduct savedCartProduct = cartProductService.addCartProduct(cartProduct);
          foundUser.getCart().getCartProducts().add(savedCartProduct);
          userRepository.save(foundUser);
           return "added successfully";

        }

    public BigDecimal calculateTotalPrice(int quantity,BigDecimal unitPrice){
        BigDecimal myQuantity = new BigDecimal(quantity);
        BigDecimal totalPrice = myQuantity.multiply(unitPrice);
        return totalPrice;
    }


    public void removeItemFromCart(Long userId, Long productId) {
            User foundUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
            Cart cart = cartRepository.findById(userId).get();
            List<CartProduct> products = cart.getCartProducts();
            for (int j = 0; j < products.size(); j++){
                if (products.get(j).getProductId().equals(productId)){
                    products.remove(products.get(j));
                }
            }
            cartProductService.removeCartProducts(productId);
            cartRepository.save(cart);
            userRepository.save(foundUser);
        }
}




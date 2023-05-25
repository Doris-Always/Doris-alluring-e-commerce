package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.exception.UserAlreadyExistException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        userRepository.save(foundUser);
        return foundUser;
    }


    @Override
    public String addToCart(Long userId, AddToCartRequest cartRequest) {
        User foundUser = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("Customer not found"));
          CartProduct cartProduct = new CartProduct();
          cartProduct.setProductName(cartRequest.getProductName());
          cartProduct.setQuantity(cartRequest.getQuantity());
          CartProduct savedCartProduct = cartProductService.addCartProduct(cartProduct);
          foundUser.getCart().getCartProducts().add(savedCartProduct);
          userRepository.save(foundUser);
           return "added successfully";

        }




    public void removeItemFromCart(Long userId, Long productId) {
            User foundUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
            var cartProducts = foundUser.getCart().getCartProducts();
            for (int i = 0; i < cartProducts.size(); i++) {
                if (cartProducts.get(i).getId().equals(productId)) {
                    cartProducts.remove(i);
                }
            }
            userRepository.save(foundUser);


        }
}




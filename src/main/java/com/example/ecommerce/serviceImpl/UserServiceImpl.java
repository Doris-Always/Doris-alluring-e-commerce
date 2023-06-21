package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.dto.response.PaymentResponse;
import com.example.ecommerce.exception.OrderNotFoundException;
import com.example.ecommerce.exception.ProductNotSufficientException;
import com.example.ecommerce.exception.UserAlreadyExistException;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    CartProductServiceImpl cartProductService;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartService cartService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartProductRepository cartProductRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderHistoryService orderHistoryService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public User register(UserRegisterRequest registerRequest) {
        Optional<User> foundUser = userRepository.findUserByEmail(registerRequest.getEmail());
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
        Optional<User> foundUser = userRepository.findUserByEmail(loginRequest.getEmail());

        if (foundUser.isPresent() && PasswordEncoder.checkPwd(loginRequest.getPassword(), foundUser.get().getPassword())){
            return new LoginResponse("login successful",HttpStatus.OK,LocalDateTime.now());

        }
        return new LoginResponse("login failed, incorrect password", HttpStatus.UNAUTHORIZED,LocalDateTime.now());

    }

    @Override
    public Long count() {
        return userRepository.count();
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();


    }

    @Override
    public Optional<User> findUserById(Long userId) {
        if (userRepository.findById(userId).isEmpty()) throw new UserNotFoundException("user not found");
        return userRepository.findById(userId);
//        return user.get();
    }

    @Override
    public User findUserByFirstName(String firstName) {
        return userRepository.findUserByFirstName(firstName);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->new UserNotFoundException("user not found"));
    }

    @Override
    public User updateUserInfo(Long userId,UpdateUserRequest updateUserRequest) {
        User foundUser = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
        foundUser.setFirstName(updateUserRequest.getFirstName());
        foundUser.setLastName(updateUserRequest.getLastName());
        foundUser.setEmail(updateUserRequest.getEmail());
        foundUser = userRepository.save(foundUser);
        return foundUser;
    }


    @Override
    public String addToCart(Long userId, AddToCartRequest cartRequest) {
        User foundUser = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("Customer not found"));
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
            User foundUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Customer not found"));
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

    @Override
    public OrderHistory orderProduct(OrderProductRequest orderProductRequest) {
        Optional<User> existingUser = findUserById(orderProductRequest.getUserId());
        if (existingUser.isEmpty()){
            throw new UserNotFoundException("user does not exist,please register");
        }
        Product existingProduct = productService.findProductById(orderProductRequest.getProductId());
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setUser(existingUser.get());
        orderHistory.setDeliveryAddress(orderProductRequest.getDeliveryAddress());
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(existingProduct);
        orderItem.setPrice(existingProduct.getPricePerUnit());
        if (orderProductRequest.getQuantity() <= existingProduct.getQuantity()) {
            orderItem.setQuantity(orderProductRequest.getQuantity());
            existingProduct.setQuantity(existingProduct.getQuantity() - orderProductRequest.getQuantity());
        }else {
            throw new ProductNotSufficientException("product quantity is not sufficient");
        }
        orderItem.setSubTotal(existingProduct.getPricePerUnit().multiply(new BigDecimal(orderProductRequest.getQuantity())));
        List<OrderItem> orderItems = orderItemRepository.findAll();
        orderItems.add(orderItem);
        orderHistory.setOrderItems(orderItems);
        productService.saveProduct(existingProduct);
        orderHistory.setPaymentStatus(PaymentStatus.PENDING);
        return orderHistoryService.saveOrder(orderHistory);
    }

    @Override
    public void cancelOrder(Long orderId) {
        OrderHistory existingOrder= orderHistoryService.findById(orderId).orElseThrow(() -> new OrderNotFoundException("order not found"));
        orderHistoryService.deleteOrderById(existingOrder.getOrderId());
    }

    @Override
    public String makePayment(Long orderId,PaymentRequest paymentRequest) {
        OrderHistory existingOrder = orderHistoryRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("order not found"));
        paymentService.pay(paymentRequest);
        existingOrder.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESSFUL);
        orderHistoryService.saveOrder(existingOrder);
        String receiver = existingOrder.getUser().getEmail();
        emailService.sendPaymentConfirmationEmail(receiver);
        return "payment successful";
    }

}




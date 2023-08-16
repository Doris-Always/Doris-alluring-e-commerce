package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.config.JwtService;
import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.PaymentResponse;
import com.example.ecommerce.dto.response.RegistrationResponse;
import com.example.ecommerce.exception.*;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
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
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SecuredUserService securedUserService;
    @Autowired
    JwtService jwtService;

    @Autowired
    ConfirmTokenService confirmTokenService;
    private final PasswordEncoder passwordEncoder;

    ModelMapper mapper = new ModelMapper();

    @Override
    public RegistrationResponse register(UserRegisterRequest registerRequest) {
        Optional<User> foundUser = userRepository.findUserByEmail(registerRequest.getEmail());
        if (foundUser.isPresent()){
            throw new UserAlreadyExistException("User Already Exist");
        }
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User user = mapper.map(registerRequest,User.class);
        user.setPassword(hashedPassword);
        Cart cart = new Cart();
        user.setCart(cart);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.CUSTOMER);
        user.setUserRoles(userRoles);
        userRepository.save(user);

        String token = generateOTP();
        emailService.sendOTP(user.getEmail(),buildEmail(registerRequest.getFirstName(), token));

        ConfirmationToken confirmationToken =
                new ConfirmationToken(token, LocalDateTime.now() ,LocalDateTime.now().plusMinutes(5),user);
        confirmationToken.setConfirmAt(LocalDateTime.now());
        confirmTokenService.saveConfirmationToken(confirmationToken);

        return RegistrationResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .message("token sent to your email")
                .build();
    }

    private String generateOTP(){
        StringBuilder generatedOtp = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        int otp =100000 + secureRandom.nextInt(900000);
        generatedOtp.append(otp);
        return generatedOtp.toString();

    }
    private String buildEmail(String name, String token) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please copy the below token to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n The token will expire in 5 minutes time. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    @Override
    public String login(LoginRequest loginRequest) {
        User foundUser = findUserByEmail(loginRequest.getEmail());

        if (!passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword())){
            throw new PasswordMismatchException("incorrect password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetails userDetails = securedUserService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        return "Bearer "+token;


    }

    @Override
    public String confirmToken(ConfirmationTokenRequest confirmationTokenRequest) {
        User foundUser = findUserByEmail(confirmationTokenRequest.getEmail());
        ConfirmationToken confirmationToken = confirmTokenService.getConfirmationToken(confirmationTokenRequest.getToken())
                .orElseThrow(()-> new TokenNotFoundException("token does not exist"));
        if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token is expired");
        }
        if (confirmationToken.getConfirmAt() != null){
            throw new IllegalStateException("token has already been confirmed");
        }

        confirmTokenService.setExpiredAt(confirmationToken.getToken());
        foundUser.setValid(true);
        userRepository.save(foundUser);
        return "token confirmed,you are now verified";
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
    public User findUserById(Long userId) {
        if (userRepository.findById(userId).isEmpty()) throw new UserNotFoundException("user not found");
        return userRepository.findById(userId).get();
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
           return "added successfully"  ;

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

        User existingUser = findUserById(orderProductRequest.getUserId());

        Product existingProduct = productService.findProductById(orderProductRequest.getProductId());
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setUser(existingUser);
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
        OrderHistory existingOrder = orderHistoryRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("order not found"));
        if (existingOrder.getPaymentStatus() == PaymentStatus.PENDING){
            paymentService.pay(paymentRequest);
        }else {
            return "payment not successful,try again";
        }
        PaymentResponse paymentResponse = new PaymentResponse();
        if (Objects.equals(paymentResponse.getMessage(), "Payment successful")){
        existingOrder.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESSFUL);
        orderHistoryService.saveOrder(existingOrder);
        String receiver = existingOrder.getUser().getEmail();
//        emailService.sendPaymentConfirmationEmail(receiver);
        }
        return "payment successful";
    }

}




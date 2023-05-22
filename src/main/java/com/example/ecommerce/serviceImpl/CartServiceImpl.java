package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.service.CartProductService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    CartProductService cartProductService;



    @Override
    public void addToCart(Cart cart) {
        cartRepository.save(cart);
    }



    @Override
    public Cart viewAllProductInCart() {
        return null;
    }

}

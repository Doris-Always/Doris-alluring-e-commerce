package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartProductRepository;
import com.example.ecommerce.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    CartProductRepository cartProductRepository;

    @Override
    public CartProduct addCartProduct(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public CartProduct findProductById(long productId) {
      return cartProductRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("product is null"));

//        return cartProduct.map(CartProduct::getId).orElse(null);

    }
}
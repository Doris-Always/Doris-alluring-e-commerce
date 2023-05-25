package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartProductRepository;
import com.example.ecommerce.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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


    }

    @Override
    public List<CartProduct> getAllCartProduct() {
        return null;
    }

    @Override
    public void deleteProductById(Long productId) {

    }

    @Override
    public void deleteAllProduct() {

    }

    @Override
    public void removeCartProducts(Long productId) {
        cartProductRepository.deleteById(productId);
    }
}

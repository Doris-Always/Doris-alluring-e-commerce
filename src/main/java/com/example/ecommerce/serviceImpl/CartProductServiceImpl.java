package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.UpdateCartProductReq;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartProductRepository;
import com.example.ecommerce.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        return cartProductRepository.findAll();
    }


    @Override
    public void deleteAllProduct() {
        cartProductRepository.deleteAll();
    }

    @Override
    public void removeCartProducts(Long productId) {
        cartProductRepository.deleteById(productId);
    }

    @Override
    public CartProduct updateCartProduct(UpdateCartProductReq updateCartProductReq) {
        CartProduct foundCartProduct =  cartProductRepository.findById(updateCartProductReq.getCartProductId())
                .orElseThrow(()->new IllegalArgumentException("product does not exist in cart"));
        foundCartProduct.setQuantity(updateCartProductReq.getQuantity());
        foundCartProduct.setTotalPrice(foundCartProduct.getPricePerUnit().multiply(new BigDecimal(updateCartProductReq.getQuantity())));
        cartProductRepository.save(foundCartProduct);
        return foundCartProduct;
    }

    @Override
    public CartProduct getCartProductByName(String name) {
        return cartProductRepository.getCartProductByProductName(name.toLowerCase());

    }


}

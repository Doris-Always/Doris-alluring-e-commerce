package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.UpdateCartProductReq;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Product;

import java.util.List;

public interface CartProductService {
    CartProduct addCartProduct(CartProduct cartProduct);

    CartProduct findProductById(long productId);

    List<CartProduct> getAllCartProduct();

   void deleteAllProduct();

    void removeCartProducts(Long productId);

    CartProduct updateCartProduct(UpdateCartProductReq updateCartProductReq);

   CartProduct getCartProductByName(String name);
}

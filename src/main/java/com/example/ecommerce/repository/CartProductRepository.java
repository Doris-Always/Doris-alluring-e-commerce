package com.example.ecommerce.repository;

import com.example.ecommerce.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
}

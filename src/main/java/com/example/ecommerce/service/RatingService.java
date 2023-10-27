package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.RatingRequest;
import com.example.ecommerce.dto.response.RatingResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Rating;

public interface RatingService {
    RatingResponse rateProduct(RatingRequest ratingRequest);

    Product findProductById(Long id);

    Rating findRatingById(Long ratingId);

}

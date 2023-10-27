package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.RatingRequest;
import com.example.ecommerce.dto.response.RatingResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Rating;
import com.example.ecommerce.service.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImplementation implements RatingService {
    @Override
    public RatingResponse rateProduct(RatingRequest ratingRequest) {
        return null;
    }

    @Override
    public Product findProductById(Long id) {
        return null;
    }

    @Override
    public Rating findRatingById(Long ratingId) {
        return null;
    }

}

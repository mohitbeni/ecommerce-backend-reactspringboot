package com.ecomm.service;

import com.ecomm.exception.ProductException;
import com.ecomm.model.Product;
import com.ecomm.model.Rating;
import com.ecomm.model.User;
import com.ecomm.repository.RatingRepository;
import com.ecomm.request.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductService productService;

    public RatingServiceImpl(){}

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {

        return ratingRepository.getAllProductsRating(productId);
    }
}

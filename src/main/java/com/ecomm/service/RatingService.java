package com.ecomm.service;

import com.ecomm.exception.ProductException;
import com.ecomm.model.Rating;
import com.ecomm.model.User;
import com.ecomm.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}

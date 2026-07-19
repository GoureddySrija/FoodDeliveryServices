package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.model.Ratings;


public interface RatingsService {
	List<Ratings> getAllRatingsByRestaurantId(int restaurant_id) throws InvalidRestaurantIdException;
}
package com.fooddelivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Repository.RatingsRepository;
import com.fooddelivery.model.Ratings;
@Service
public class RatingsServiceImpl implements RatingsService {
    
	@Autowired
    private RatingsRepository ratingsRepository;
	
	@Override
	public List<Ratings> getAllRatingsByRestaurantId(int restaurant_id) throws InvalidRestaurantIdException {
		List<Ratings> ratings = ratingsRepository.findByRestaurantId(restaurant_id);
		if(ratings.isEmpty()) {
			throw new InvalidRestaurantIdException("No ratings found for restaurant with ID"+restaurant_id);
		}
		return ratings;

	} 

}
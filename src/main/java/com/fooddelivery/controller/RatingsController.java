package com.fooddelivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.model.Ratings;
import com.fooddelivery.service.RatingsService;

@RestController
@RequestMapping("/api/restaurants")
public class RatingsController {
	
	@Autowired
    private RatingsService ratingsService;
    
    @GetMapping("/{restaurant_id}/ratings")
    public ResponseEntity<List<Ratings>> getAllRatingsByRestaurantId(@PathVariable int restaurant_id) throws InvalidRestaurantIdException{
    	
    	System.out.println("Ratings for the restaurant retrieved successfully.");
    	List<Ratings> ratings =ratingsService.getAllRatingsByRestaurantId(restaurant_id);
    	if(ratings.isEmpty()) {
    		throw new InvalidRestaurantIdException("Invalid restaurant ID");
    	}
    	return new ResponseEntity<>(ratings,HttpStatus.OK);

    }
}

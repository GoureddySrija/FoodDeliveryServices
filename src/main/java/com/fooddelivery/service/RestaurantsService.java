package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.model.Restaurants;

public interface RestaurantsService {
	 List<Restaurants> getAllRestaurants();
	 Restaurants updateRestaurants(Restaurants restaurants) throws InvalidRestaurantIdException;
	 public Restaurants getRestaurantsById(int restaurantId);
	 void deleteRestaurantsById(int restaurantId) throws InvalidRestaurantIdException;
     public Restaurants addRestaurants(Restaurants restaurants);

	 
	 
}
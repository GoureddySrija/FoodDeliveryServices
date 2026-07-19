package com.fooddelivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.model.Restaurants;
import com.fooddelivery.service.RestaurantsService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsController {
	@Autowired
	private RestaurantsService restaurantsService;
	
	@GetMapping(consumes = "application/json", produces="application/json")
	public ResponseEntity<List<Restaurants>> getAllRestaurants(){
		System.out.println("Restaurants fetched Successfully");
		List<Restaurants> restaurants = restaurantsService.getAllRestaurants();
		return new ResponseEntity<>(restaurants,HttpStatus.OK);
	}
	
	@PutMapping("/{restaurant_id}/update")
	ResponseEntity<Restaurants> updateRestaurants(@RequestBody Restaurants rest) throws InvalidRestaurantIdException{
		
		Restaurants restaurants=restaurantsService.updateRestaurants(rest);
		if(rest.getRestaurant_id()<=0) {
			throw new InvalidRestaurantIdException("Invalid restaurant ID: "+rest.getRestaurant_id());
		}
		System.out.println("Restuarant details updated successfully");		
		return new ResponseEntity<Restaurants>( restaurants,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("{restaurantId}")
	public ResponseEntity<String> getRestaurantsById(@PathVariable("restaurantId")int restaurantId){
	System.out.println("Restaurant details fetched successfully");
	try {
		Restaurants restaurants = restaurantsService.getRestaurantsById(restaurantId);
		return ResponseEntity.ok(restaurants.toString());
	}catch(NoSuchRestaurantIDException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant with ID  "+ restaurantId +"not found");
	}
	}
 
	@PostMapping
	public ResponseEntity<String> addRestaurants(@RequestBody Restaurants restaurants) throws DuplicateRestaurantIDException{
			System.out.println("Restaurant created successfully");
			try {
				Restaurants addedRestaurant = restaurantsService.addRestaurants(restaurants);
				return ResponseEntity.ok(addedRestaurant.toString());
			}catch(DuplicateRestaurantIDException e) {
				return ResponseEntity.badRequest().body(e.getMessage());	
			}
	}
	@DeleteMapping("{restaurantId}")
	public ResponseEntity<Void> deleteRestaurantsById(@PathVariable int restaurantId) throws InvalidRestaurantIdException{
		System.out.println("Restaurant deleted successfully");
		restaurantsService.deleteRestaurantsById(restaurantId);
		return ResponseEntity.noContent().build();
	}
	
	
	

}

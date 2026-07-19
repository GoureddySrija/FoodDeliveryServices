package com.fooddelivery.Exception;

public class DuplicateRestaurantIDException extends RuntimeException {
	public DuplicateRestaurantIDException(String message) {
		super(message);
	}
 
}
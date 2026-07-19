package com.fooddelivery.Exception;

public class NoSuchRestaurantIDException extends RuntimeException{
	public NoSuchRestaurantIDException(String message){
		super(message);
	}
 
}
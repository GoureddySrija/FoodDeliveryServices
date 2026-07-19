package com.fooddelivery.Exception;

public class CustomerNotFoundException extends RuntimeException{
	public CustomerNotFoundException(String message) {
		super(message);
	}
 
}

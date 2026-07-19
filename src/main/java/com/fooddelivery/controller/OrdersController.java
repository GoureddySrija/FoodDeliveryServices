
package com.fooddelivery.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.model.Orders;
import com.fooddelivery.service.OrdersService;

 
@RestController

@RequestMapping("/api/orders")

public class OrdersController {

	@Autowired
	private OrdersService ordersService;
 
	public OrdersController(OrdersService ordersService) {

		this.ordersService = ordersService;

	}

	@PostMapping(consumes = "application/json",produces = "application/json")

	public ResponseEntity<Orders> PlaceOrder(@RequestBody Orders orders)throws DuplicateOrderIdException{

		try {

		Orders savedOrder=ordersService.placeOrder(orders);

		System.out.println("Order placed successfully");

		return new ResponseEntity<Orders>(savedOrder,HttpStatus.CREATED);

		}

		catch(Exception e) {

			throw new DuplicateOrderIdException("Order with Id "+orders.getOrder_id()+" already exist");

		}

	}

	@GetMapping(value="/{orderId}",consumes = "application/json",produces = "application/json")

	public ResponseEntity<Orders> getOrders(@PathVariable int orderId) throws OrdersNotFoundException,InvalidOrderIdException {

		Orders orders=ordersService.getOrders(orderId);

		if(orders==null ) {

			System.out.println("Orders Not Found");

			throw new OrdersNotFoundException("Orders not found with Id :"+orderId);

		}

//		if(orderId<=0) {
//
//			throw new InvalidOrderIdException("Order Id"+orderId+" is Invalid ");
//
//		}

		System.out.println("Order details fetched successfully");

		return new ResponseEntity<Orders>(orders,HttpStatus.OK);

	}

	@GetMapping(consumes = "application/json",produces = "application/json")

	public ResponseEntity<List<Orders>> getAllOrders(){

		List<Orders> orders =ordersService.getAllOrders();

		System.out.println("Order details fetched successfully");

		return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);

	}

	@PutMapping(value="/{orderId}/status",consumes = "application/json",produces = "application/json")

	public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestBody String newStatus) throws InvalidOrderIdException, OrdersNotFoundException {

		if(orderId<=0) {

			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");

		}

		Orders orders=ordersService.getOrders(orderId);

		if(orders==null) {

			return ResponseEntity.notFound().build();

		}

		orders.setOrder_status(newStatus);

		ordersService.UpdateOrderStatus(orderId, newStatus);

		System.out.println("Order status updated successfully");

		return ResponseEntity.accepted().body(orders.getOrder_status());

		
 
	}

	@DeleteMapping(value="/{orderId}",consumes = "application/json",produces = "application/json")

	public ResponseEntity<String> cancelOrder(@PathVariable int orderId) throws InvalidOrderIdException{

		if(orderId<=0) {

			throw new InvalidOrderIdException("Order Id with "+orderId+" is invalid ");

		}

		boolean deleted=ordersService.cancelOrder(orderId);

		if(deleted) {

			return ResponseEntity.ok("Order canceled successfully");

		}

		else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order not found");

		}

	}
}
	
	
 

package com.fooddelivery.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fooddelivery.Exception.CustomException;
import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.model.Customers;
import com.fooddelivery.model.Orders;
import com.fooddelivery.service.CustomersService;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
	@Autowired
	private CustomersService customersService;

	public CustomersController(CustomersService customersService) {
		this.customersService = customersService;
	}
    
	@GetMapping(consumes = "application/json", produces="application/json")
	  public ResponseEntity<List<Customers>> getAllCustomers(){
		  List<Customers> customers= customersService.getAllCustomers();
		  if(customers.isEmpty()) {
			  return ResponseEntity.notFound().build();
		  }
			  return ResponseEntity.ok().body(customers);
	  }
    
	@GetMapping(value="/{customer_id}",produces="application/json")
	  public ResponseEntity<Customers> getCustomerById(@PathVariable int customer_id){
		  try {
			  Customers customer=customersService.getCustomerById(customer_id);
			  return ResponseEntity.ok().body(customer);
		  }catch(CustomerNotFoundException e) {
			  return ResponseEntity.notFound().build();
		  }
	  }

	@PostMapping
	public String addCustomer(@RequestBody Customers customer) {
		try {
			if (customersService.getCustomerById(customer.getCustomer_id()) != null) {
				throw new DuplicateCustomerIDException(
						"Customer with ID " + customer.getCustomer_id() + " already exists");
			}
			Customers savedCustomer = customersService.addCustomer(customer);
			return "Customer Create successfully with ID:" + savedCustomer.getCustomer_id();
		} catch (DuplicateCustomerIDException e) {
			return " Conflict: " + e.getMessage();
		}
	}

	@PutMapping(value = "/{customer_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Customers> updateCustomerById(@PathVariable("customer_id") int customer_id,
			@RequestBody Customers updatedCustomer) {
		Customers customer = customersService.getCustomerById(customer_id);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		customer.setCustomer_name(updatedCustomer.getCustomer_name());
		customer.setCustomer_email(updatedCustomer.getCustomer_email());
		customer.setCustomer_phone(updatedCustomer.getCustomer_phone());
		Customers savedCustomer = customersService.updateCustomer(customer);
		return ResponseEntity.ok().body(savedCustomer);
	}

	@DeleteMapping("/{customerId}")
	ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Integer customerId ) throws CustomException {
	    customersService.deleteCustomerByID(customerId);
	    String jsonResponse = "{\"message\": \"Customers are deleted successfully\", \"code\": \"200\"}";
	    return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
	}
    }


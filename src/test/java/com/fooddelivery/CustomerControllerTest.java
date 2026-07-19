
package com.fooddelivery;
 
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.ArgumentMatchers.intThat;

import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.doThrow;

import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 
import java.awt.PageAttributes.MediaType;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import java.util.Optional;
 
import org.assertj.core.util.Arrays;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
 



import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.controller.CustomersController;
import com.fooddelivery.model.Customers;
import com.fooddelivery.service.CustomersService;
 
@ExtendWith(MockitoExtension.class)

@SpringBootTest

@AutoConfigureMockMvc

public class CustomerControllerTest {

	@Mock

	private CustomersService customerService=mock(CustomersService.class);

	@InjectMocks

	private CustomersController customerController=new CustomersController(customerService);
	


	@Test

	public void testGetAllCustomers() {

		Customers customer1= new Customers(1,"John","john@example.com","1234567890");

		Customers customer2= new Customers(2,"Alice","alice@example.com","9876543210");

		List<Customers> customersList=new ArrayList<>();

		customersList.add(customer1);

		customersList.add(customer2);

		when(customerService.getAllCustomers()).thenReturn(customersList);

		ResponseEntity<List<Customers>> responseEntity=customerController.getAllCustomers();

		assert responseEntity.getStatusCode()==HttpStatus.OK;

		List<Customers> returnedCustomersList=responseEntity.getBody();

		assert returnedCustomersList != null && returnedCustomersList.size()==2;

		assert returnedCustomersList.get(0).getCustomer_id()==1;

		assert returnedCustomersList.get(0).getCustomer_name().equals("John");

		assert returnedCustomersList.get(1).getCustomer_id()==2;

		assert returnedCustomersList.get(1).getCustomer_name().equals("Alice");

	}

	@Test

	public void testGetAllCustomers_negative() {

		when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

		ResponseEntity<List<Customers>> responseEntity=customerController.getAllCustomers();

		assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());

	}

	@Test

	public void testGetCustomerById() {

		Customers customer = new Customers(1,"John","john@example.com","1234567890");

		when(customerService.getCustomerById(1)).thenReturn(customer);

		ResponseEntity<Customers> responseEntity=customerController.getCustomerById(1);

		assert responseEntity.getStatusCode()==HttpStatus.OK;

		Customers returnedCustomer=responseEntity.getBody();

		assert returnedCustomer !=null;

		assert returnedCustomer.getCustomer_id()==1;

		assert returnedCustomer.getCustomer_name().equals("John");

		assert returnedCustomer.getCustomer_email().equals("john@example.com");

		assert returnedCustomer.getCustomer_phone().equals("1234567890");		

	}

	@Test

	public void testGetCustomerById_negative() {

		//String expectedErrorMessage="Customer Not Found";

		//Customers expectedCustomer=new Customers();

		when(customerService.getCustomerById(anyInt())).thenThrow(CustomerNotFoundException.class);

		ResponseEntity<Customers> responseEntity=customerController.getCustomerById(1);

		verify(customerService).getCustomerById(1);

		assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());	

		assertNull(responseEntity.getBody());
 
	}
 
	

	@Test

	public void testUpdateCustomerById() {

		Customers existingCustomer=new Customers(1,"John","john@example.com","1234567890");

		Customers updateCustomer=new Customers(1,"John Doe","johndoe@example.com","9876543210");

		when(customerService.getCustomerById(1)).thenReturn(existingCustomer);

		when(customerService.updateCustomer(any(Customers.class))).thenReturn(updateCustomer);

		ResponseEntity<Customers> responseEntity=customerController.updateCustomerById(1, updateCustomer);

		//assert responseEntity.getStatusCode()==HttpStatus.OK;

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Customers returnCustomer=responseEntity.getBody();

		assertNotNull(returnCustomer);

		assertEquals(1,returnCustomer.getCustomer_id());

		assertEquals("John Doe",returnCustomer.getCustomer_name());

		assertEquals("johndoe@example.com",returnCustomer.getCustomer_email());

		assertEquals("9876543210",returnCustomer.getCustomer_phone());

 
	}

	@Test

	public void testUpdateCustomerByIdNegative() {

		when(customerService.getCustomerById(1)).thenReturn(empty());

		ResponseEntity<Customers> responseEntity=customerController.updateCustomerById(1, new Customers());

		assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());

	}
 
	private Customers empty() {

		// TODO Auto-generated method stub

		return null;

	}

//	@Test

//	public void testDeleteCustomerById() {

//		int customerId=1;

//		doThrow(CustomerNotFoundException.class).when(customerService).deleteCustomerById(customerId);

//		ResponseEntity<?> responseEntity=customerController.deleteCustomerById(customerId);

//		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

//		assertNull(responseEntity.getBody());

//	}

//

//	@Test

//	public void testDeleteCustomerById_negative() {

//		

//		

//		int customerId=8;

//		when(customerService.getCustomerById(customerId)).thenReturn(null);

//		//doThrow(CustomerNotFoundException.class).when(customerService).deleteCustomerById(customerId);

//		ResponseEntity<String> responseEntity=customerController.deleteCustomerById(customerId);

//		verify(customerService).getCustomerById(customerId);

//		assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());

//		assertNull(responseEntity.getBody());

//		

//	}

}

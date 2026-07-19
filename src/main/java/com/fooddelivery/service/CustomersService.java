package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.CustomException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.model.Customers;
import com.fooddelivery.model.Orders;
 
 
public interface CustomersService {
  List<Customers> getAllCustomers();
  Customers addCustomer(Customers customer) throws DuplicateCustomerIDException;
  Customers getCustomerById(int customer_id);
  Customers updateCustomer(Customers customer);
  void deleteCustomerByID(int custID) throws CustomException;
  List<Orders> getOrdersByCustomerId(int customer_id);
}	
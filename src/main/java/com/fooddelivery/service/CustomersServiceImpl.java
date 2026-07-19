package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.CustomException;
import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.Repository.CustomersRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.model.Customers;
import com.fooddelivery.model.Orders;

import jakarta.transaction.Transactional;
 
@Service
public class CustomersServiceImpl implements CustomersService {
	@Autowired
	private CustomersRepository customersRepository;
	@Autowired
	private OrdersRepository ordersRepository;
 
 
	public CustomersServiceImpl(CustomersRepository customersRepository) {
		this.customersRepository = customersRepository;
	}
 
	@Override
	public List<Customers> getAllCustomers()throws CustomerNotFoundException {
		
		List<Customers> customers=customersRepository.findAll();
		if(customers.isEmpty()) {
			throw new CustomerNotFoundException("No customers found");
		}
		return customers;
	}
	

	 
	@Override
	public Customers addCustomer(Customers customer) throws DuplicateCustomerIDException{
    Optional<Customers> existingCustomer = customersRepository.findById(customer.getCustomer_id());
    if(existingCustomer.isPresent()) {
    	throw new DuplicateCustomerIDException("Customer with Id"+customer.getCustomer_id()+" already exists");
    }else {
		return customersRepository.save(customer);
	}
	} 
	@Override
	public Customers getCustomerById(int customer_id) {
		Optional<Customers> optionalCustomer=customersRepository.findById(customer_id);
		return optionalCustomer.orElse(null) ;
	}
 
	@Override
	public Customers updateCustomer(Customers customer) {
		return customersRepository.save(customer);
	}
   
 
	@Override
	public List<Orders> getOrdersByCustomerId(int customer_id) {
		return ordersRepository.findOrdersByCustomerId(customer_id);
	}

	@Override
    @Transactional
     public void deleteCustomerByID(int custID) throws CustomException{
   	 Optional<Customers> optionalCustomer = customersRepository.findById(custID);
        if (optionalCustomer.isEmpty()) {
            throw new CustomException("Item not found");
        } else {
        	customersRepository.deleteById(custID);
        }
    }
	
//	@Override
//	@Transactional
//	public boolean deleteCustomerByID(int custID ) throws CustomException {
//		Optional<Customers> optionalCustomer = customersRepository.findById(custID);
//		
//		if( optionalCustomer.isPresent())
//		{
//			ordersRepository.deleteCustomerById(custID);
//			customersRepository.deleteById(custID);
//			return true;
//		}
//		return false;
//	}
	
	
	
 
}
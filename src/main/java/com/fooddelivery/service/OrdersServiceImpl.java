package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
//import com.fooddelivery.Repository.OrderItemsRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.model.Orders;

import jakarta.transaction.Transactional;

 
@Service
public class OrdersServiceImpl implements OrdersService{
 
	@Autowired
	private OrdersRepository ordersRepository;

	
	@Override
	public List<Orders> getOrderByCustomerId(int customer_id) {
		return ordersRepository.findOrdersByCustomerId(customer_id);
	}
	
	@Override
	@Transactional
	public Orders placeOrder(Orders orders) throws DuplicateOrderIdException{  
		Optional<Orders> existingOrder=ordersRepository.findById(orders.getOrder_id());
		if(existingOrder.isPresent()) {
			throw new DuplicateOrderIdException("Order with Id "+orders.getOrder_id()+" already exist");
		}
		else {
			return ordersRepository.save(orders);
		}
	}
 
	@Override
	@Transactional
	public Orders getOrders(int orderId) throws OrdersNotFoundException{
		
		Optional<Orders> orders=ordersRepository.findById(orderId);
		if(orders.isEmpty()) {
			throw new OrdersNotFoundException("Orders not found with Id: "+orderId);
		}
		
		return orders.orElse(null);
	}
 
	@Override
	@Transactional
	public List<Orders> getAllOrders() {
		return ordersRepository.findAll();
	}
 
	@Override
	@Transactional
	public Orders UpdateOrderStatus(int orderId, String newStatus) throws InvalidOrderIdException, OrdersNotFoundException{
		Optional<Orders> OpOrders=ordersRepository.findById(orderId);
		if(OpOrders.isPresent()) {
			Orders orders=OpOrders.get();
			orders.setOrder_status(newStatus);
			ordersRepository.save(orders);
			return ordersRepository.save(orders);
		}
		else if(orderId<=0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		else {
			throw new OrdersNotFoundException("Order not found with id: "+orderId);
		}
 
	}
 
	@Override
	@Transactional
	public boolean cancelOrder(int orderId) throws InvalidOrderIdException{
		Optional<Orders> optionalOrders=ordersRepository.findById(orderId);
		if(optionalOrders.isPresent()) {
			ordersRepository.deleteById(orderId);
			return true;
		}
		if(orderId<=0) {
			throw new InvalidOrderIdException("Order Id with "+orderId+" is not present in the database ");
		}
		return false;
}
	
	

 
}
package com.fooddelivery.service;

	import java.util.List;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.model.Orders;
	 
	public interface OrdersService {
		List<Orders> getOrderByCustomerId(int customer_id);
		public Orders placeOrder(Orders orders)throws DuplicateOrderIdException;
		public Orders getOrders(int orderId) throws OrdersNotFoundException;
		public List<Orders> getAllOrders();
		public Orders UpdateOrderStatus(int orderId,String newStatus) throws InvalidOrderIdException, OrdersNotFoundException;
		public boolean cancelOrder(int orderId) throws InvalidOrderIdException;
		
	 
	}

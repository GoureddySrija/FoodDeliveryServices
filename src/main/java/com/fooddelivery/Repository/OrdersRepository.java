package com.fooddelivery.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer>{
	@Query("SELECT o FROM Orders o Where o.customers.customer_id=:customer_id")
	List<Orders> findOrdersByCustomerId(@Param("customer_id")int customer_id);
//    @Query("DELETE FROM Orders o WHERE o.customers.customer_id=:customer_id")
//	void deleteCustomerById(@Param("custID") int custID);
}

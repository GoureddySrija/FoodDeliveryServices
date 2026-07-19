package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.OrderItems;
@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Integer> {
//	@Modifying
//	@Query("DELETE FROM OrderItems oi WHERE oi.orders.orderId=:orderId")
//	void deleteByOrderId( int orderId);

}

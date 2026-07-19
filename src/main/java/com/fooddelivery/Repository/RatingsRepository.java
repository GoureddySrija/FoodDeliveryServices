package com.fooddelivery.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.Ratings;

@Repository
public interface RatingsRepository extends CrudRepository<Ratings,Integer> {
	@Query("SELECT r FROM Ratings r WHERE r.restaurants.restaurant_id=:restaurant_id")
	List<Ratings> findByRestaurantId(@Param("restaurant_id")int restaurant_id);
//	@Modifying
//   @Query("DELETE FROM Ratings r WHERE r.orders.orderId=:orderId")
//	void deleteByOrderId( int orderId);
}
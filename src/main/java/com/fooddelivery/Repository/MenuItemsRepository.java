package com.fooddelivery.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.MenuItems;

@Repository
public interface MenuItemsRepository extends CrudRepository<MenuItems,Integer> {
    @Query("SELECT mi FROM MenuItems mi WHERE mi.restaurants.restaurant_id=:restaurant_id")
	List<MenuItems> findByRestaurantId(@Param("restaurant_id")int restaurant_id);
    @Query("SELECT mi FROM MenuItems mi WHERE mi.id = :item_id AND mi.restaurants.restaurant_id = :restaurant_id")
	MenuItems findByIdAndRestaurantId(int item_id, int restaurant_id);
 
	@Modifying
	@Query("DELETE FROM MenuItems mi WHERE mi.restaurants.restaurant_id =:restaurant_id AND mi.id=:item_id")
	void deleteByRestaurantIdAndItemId(int restaurant_id, int item_id);
   
}
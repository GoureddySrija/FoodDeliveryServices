package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.InvalidItemIdException;
import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.model.MenuItems;

public interface MenuItemsService {
     List<MenuItems> getMenuItemsByRestaurantId(int restaurant_id);
     public MenuItems addmenuItems(int restaurant_id, MenuItems menuItems)throws InvalidRestaurantIdException;
 	public MenuItems updatemenuItems(int restaurant_id,int item_id ,MenuItems menuItems)throws InvalidMenuItemException;
 	public void deletemenuItems(int restaurant_id,int item_id)throws InvalidItemIdException;
	MenuItems findByIdAndRestaurantId(int itemId, int restaurantId);
}

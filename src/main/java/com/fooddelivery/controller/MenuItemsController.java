package com.fooddelivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddelivery.Exception.InvalidItemIdException;
import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.model.MenuItems;
import com.fooddelivery.service.MenuItemsService;

@RestController

@RequestMapping("/api/restaurants")

public class MenuItemsController {

	@Autowired // injection using constructor

	private MenuItemsService menuItemsService;

	@GetMapping("/{restaurant_id}/menu")

	public ResponseEntity<List<MenuItems>> getMenuItemsByRestaurantId(@PathVariable int restaurant_id) {
		System.out.println("Collection of Menu Items for the restaurant");
		List<MenuItems> menuItem = menuItemsService.getMenuItemsByRestaurantId(restaurant_id);
		if (menuItem.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(menuItem, HttpStatus.OK);
		}
	}

	@PostMapping("/{restaurant_id}/menu")
	public ResponseEntity<MenuItems> addmenuItemsByRestaurantId(@PathVariable int restaurant_id,
			@RequestBody MenuItems menuItems) throws InvalidRestaurantIdException {
		MenuItems savedItems = menuItemsService.addmenuItems(restaurant_id, menuItems);
		if (savedItems == null) {
			throw new InvalidRestaurantIdException("Restaurant Id is not found with Id: " + restaurant_id);
		}
		System.out.println("Menu item added to the restaurant successfully");
		return new ResponseEntity<>(savedItems, HttpStatus.CREATED);

	}

	@PutMapping("/{restaurant_id}/menu/{item_id}")

	public ResponseEntity<MenuItems> updatemenuItems(@PathVariable int restaurant_id, @PathVariable int item_id,
			@RequestBody MenuItems menuItems) throws InvalidMenuItemException {

		if (menuItems.getItem_name() == null || menuItems.getItem_name().isEmpty()) {

			throw new InvalidMenuItemException("Item Name cannot be empty");

		}

		if (menuItems.getItem_price() <= 0) {

			throw new InvalidMenuItemException("Price must be greater than 0");

		}

		MenuItems updatedItems = menuItemsService.updatemenuItems(restaurant_id, item_id, menuItems);

		System.out.println("Menu item details updated successfully");

		return new ResponseEntity<>(updatedItems, HttpStatus.OK);

	}
	@DeleteMapping("/{restaurant_id}/menu/{item_id}")

	public ResponseEntity<MenuItems> deletemenuItems(@PathVariable int restaurant_id, @PathVariable int item_id)
			throws InvalidItemIdException {

		try {

			menuItemsService.deletemenuItems(restaurant_id, item_id);

			System.out.println("Menu item deleted successfully");

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}

		catch (InvalidItemIdException e) {

			throw new InvalidItemIdException("Item Id is not found with Id: " + item_id);

		}

	}

}

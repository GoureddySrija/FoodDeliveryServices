package com.fooddelivery.controller.thymeleaf;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.model.MenuItems;
import com.fooddelivery.service.MenuItemsService;
 
 
@Controller
@RequestMapping("/api")
public class MenuItemsThymeleaf {
	@Autowired
    private MenuItemsService menuItemsService;
	//HOME PAGE
	

	@GetMapping("menuitems")
    public String showMenuItems() {
        return "menu_items"; // Assuming your homepage HTML file is named "index.html"
    }
 
	//GET ALL MENU ITEMS
    @GetMapping("/{restaurant_id}/menuItems")
    public String getMenuItemsByRestaurantId(@PathVariable int restaurant_id, Model model) {
        List<MenuItems> menuItems = menuItemsService.getMenuItemsByRestaurantId(restaurant_id);
        model.addAttribute("menuItems", menuItems);
        return "getmenuItems"; // Return the name of the Thymeleaf template
    }
     // ADD MENU ITEMS 
    @GetMapping("/{restaurant_id}/menu/add")
    public String showAddMenuItemForm(@PathVariable int restaurant_id, Model model) {
        // Create a new MenuItems object to bind the form data
        MenuItems menuItems = new MenuItems();
        // Set default values or perform any necessary initialization
        model.addAttribute("restaurant_id", restaurant_id);
        model.addAttribute("menuItems", menuItems);
        return "addmenuItems"; // Return the HTML page for adding menu items
    }
    @PostMapping("/{restaurant_id}/menu/add")
    public String addmenuItems(@PathVariable int restaurant_id, @ModelAttribute MenuItems menuItems) {
        try {
            // Call the service method to add the menu item
            menuItemsService.addmenuItems(restaurant_id, menuItems);
            // Redirect to the home page if the addition was successful
            return "redirect:/api/menuitems";
        } catch (Exception e) {
            // Handle any exceptions, log the error, and return an error page
            e.printStackTrace(); // Log the exception for debugging
            return "errorPage";
        }
    }

    // Get mapping to display the form for updating a menu item

    @GetMapping("/{restaurantId}/menu/update/{itemId}")
    public String showUpdateMenuItemForm(@PathVariable int restaurantId, @PathVariable int itemId, Model model) {
        MenuItems menuItems = menuItemsService.findByIdAndRestaurantId(itemId, restaurantId);
        model.addAttribute("menuItems", menuItems);
        return "update_menu_item";
    }
 
    @PostMapping("/{restaurantId}/menu/update/{itemId}")
    public String updateMenuItem(@PathVariable int restaurantId, @PathVariable int itemId, @ModelAttribute MenuItems menuItems) throws InvalidMenuItemException {
        menuItemsService.updatemenuItems(restaurantId, itemId, menuItems);
        return "redirect:/api/menuitems"; // Assuming home page URL is /home
    }
    }
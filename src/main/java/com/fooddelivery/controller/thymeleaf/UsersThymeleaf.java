package com.fooddelivery.controller.thymeleaf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.model.Ratings;
import com.fooddelivery.model.Restaurants;
import com.fooddelivery.service.RatingsService;
import com.fooddelivery.service.RestaurantsService;

@Controller
@RequestMapping("/api")
public class UsersThymeleaf {
	
	@Autowired
	private RestaurantsService restaurantsService;
	@Autowired
	private RatingsService ratingsService;
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	@PostMapping("/login")
    public String authenticate() {
        // Perform authentication logic here
        // If authentication is successful, redirect to the index page
        return "redirect:/home";
    }
	@GetMapping("/home")
    public String home() {
        return "Home";
    }
	
	@GetMapping("/restaurant")
	public String restaurant() {
		return "Restaurants";
	}
	
	@GetMapping("/restaurants")
    public String getAllRestaurants(Model model) {
		List<Restaurants> restaurants = restaurantsService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "GetAllRestaurants"; // This is the name of the Thymeleaf template
    }
	 @GetMapping("/Customer.html")
	    public String showCustomersPage() {
	        return "Customer";
	    }
	@GetMapping("/update/{restaurant_id}")
	public String showUpdateRestaurantForm(@PathVariable("restaurant_id") int restaurantId, Model model) {
	    // Fetch the restaurant from the database using restaurant ID
	    Restaurants restaurant = restaurantsService.getRestaurantsById(restaurantId);
	    // Add the restaurant to the model
	    model.addAttribute("restaurant", restaurant);
	    return "UpdateRestaurants"; // Return the HTML page for updating restaurant
	}

	@PostMapping("/update/{restaurant_id}")
	public String updateRestaurants(@PathVariable("restaurant_id") @ModelAttribute("restaurant") int restaurantId ,Restaurants updatedRestaurant) throws InvalidRestaurantIdException {
	    // Update restaurant details using restaurantService
		updatedRestaurant.setRestaurant_id(restaurantId);
	    restaurantsService.updateRestaurants(updatedRestaurant);
	    return "redirect:/api/home"; // Redirect to restaurant list page after updating
	}
	
	@GetMapping("/{restaurantId}")
    public String getRestaurantById(@PathVariable("restaurantId") int restaurantId, Model model) {
        try {
            Restaurants restaurant = restaurantsService.getRestaurantsById(restaurantId);
            model.addAttribute("restaurant", restaurant);
            return "GetRestaurantById"; // This should be the name of your Thymeleaf HTML template
        } catch (NoSuchRestaurantIDException e) {
            model.addAttribute("errorMessage", "Restaurant with ID " + restaurantId + " not found");
            return "error"; // Assuming you have an error template to handle such cases
        }
    }
	
	
	@GetMapping("/{restaurant_id}/ratings")
    public String getAllRatingsByRestaurantId(@PathVariable int restaurant_id, Model model) throws InvalidRestaurantIdException {
        System.out.println("Ratings for the restaurant retrieved successfully.");
        List<Ratings> ratings = ratingsService.getAllRatingsByRestaurantId(restaurant_id);
        if (ratings.isEmpty()) {
            throw new InvalidRestaurantIdException("Invalid restaurant ID");
        }
        // Add ratings to the model to be displayed in the Thymeleaf template
        model.addAttribute("ratings", ratings);
        return "GetAllRatings"; // Return the Thymeleaf template name
    }
	
	
	@GetMapping("/add-restaurant")
    public String showAddRestaurantForm(Model model) {
        model.addAttribute("restaurant", new Restaurants());
        return "add_restaurant";
    }

    @PostMapping("/add")
    public String addRestaurant(@ModelAttribute("restaurant") Restaurants restaurant) {
        restaurantsService.addRestaurants(restaurant);
        return "redirect:/api/restaurant"; // Redirect to the home page
    }
}


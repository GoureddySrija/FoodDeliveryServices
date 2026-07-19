package com.fooddelivery.controller.thymeleaf;
 
import java.time.LocalDateTime;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.model.Orders;
import com.fooddelivery.service.OrdersService;

 
@Controller
public class OrderControllerThymeleaf {
	 @Autowired
	    private OrdersService ordersService;

	 @GetMapping("/")
	    public String home() {
	        return "index"; // Assuming your homepage HTML file is named "index.html"
	    }
	 @GetMapping("/OrderSubPage")
	    public String OrderSub() {
	        return "OrderSubPage"; // Assuming your homepage HTML file is named "index.html"
	    }
 
	    @GetMapping(value="/orders/{orderId}")
	    public String getOrderDetails(@PathVariable int orderId, Model model) {
	        try {
	            Orders order = ordersService.getOrders(orderId);
	            model.addAttribute("order", order);
	            return "orderDetails";
	        } catch(OrdersNotFoundException e) {
	            return "orderDetails";
	        }
	    }
	    @GetMapping("/orders/{orderId}/update")
	    public String showUpdateForm(@PathVariable("orderId") int orderId, Model model) throws OrdersNotFoundException, InvalidOrderIdException {
	        // Fetch the order from the database using orderId
	        Orders order = ordersService.getOrders(orderId);
	        // Add the order to the model
	        model.addAttribute("order", order);
	        return "updateOrderStatus"; // Return the HTML page for updating order status
	    }
 
	    @PostMapping("/orders/update")
	    public String updateOrderStatus(@ModelAttribute("order") Orders order) throws InvalidOrderIdException, OrdersNotFoundException {
	        // Update order status using orderService
	        ordersService.UpdateOrderStatus(order.getOrder_id(), order.getOrder_status());
	        return "redirect:/OrderSubPage"; // Redirect to the homepage or any other page after updating
	    }
	    @GetMapping("/orderForm")
	    public ModelAndView showOrderForm() {
	        ModelAndView modelAndView = new ModelAndView("order-form");
	        modelAndView.addObject("order", new Orders());
	        return modelAndView;
	    }
	    @PostMapping("/placeOrder")
	    public String placeOrder(@ModelAttribute("order") Orders order, RedirectAttributes redirectAttributes) {
	        try {
	            order.setOrder_date(LocalDateTime.now()); // Auto-generate order date
	            // Assuming foreign keys default to null
	            order.setCustomers(null);
	            order.setRestaurants(null);
	            order.setDeliveryDrivers(null);
	            Orders savedOrder = ordersService.placeOrder(order);
	            redirectAttributes.addFlashAttribute("message", "Order placed successfully");
	            return "redirect:/OrderSubPage"; // Redirect to home page
	        } catch (DuplicateOrderIdException e) {
	            redirectAttributes.addFlashAttribute("error", "Order with ID " + order.getOrder_id() + " already exists");
	            return "redirect:/orderForm"; // Redirect back to order form
	        }
	    }

}
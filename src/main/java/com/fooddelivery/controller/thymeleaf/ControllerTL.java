package com.fooddelivery.controller.thymeleaf;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.model.Customers;
import com.fooddelivery.model.Orders;
import com.fooddelivery.service.CustomersService;
 
 
@Controller
@RequestMapping("/customers")
public class ControllerTL {
 
    private final CustomersService customerService;
 
 
    public ControllerTL(CustomersService customerService) {
		super();
		this.customerService = customerService;
	}
 
 
	@GetMapping("/html")
    public String getAllCustomersHtml(Model model) {
        List<Customers> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "Customers"; 
    }
 
	@GetMapping(value="/{customer_id}")
	public String getCustomerDetails(@PathVariable int customer_id, Model model) {
	    try {
	        Customers customer = customerService.getCustomerById(customer_id);
	        model.addAttribute("customer", customer);
	        return "customerDetails"; // This should match the name of your Thymeleaf template file
	    } catch(CustomerNotFoundException e) {
	        return "customerNotFound"; // You can create a separate Thymeleaf template for displaying a customer not found message
	    }
	}
	@PostMapping("/add")
	public String addCustomer( Customers customer, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        String errorMessage = result.getAllErrors().stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .collect(Collectors.joining("; "));
	        model.addAttribute("errorMessage", errorMessage);
	        return "error"; 
	    }
 
	    try {
	        Customers existingCustomer = customerService.getCustomerById(customer.getCustomer_id());
	        if (existingCustomer != null) {
	            throw new DuplicateCustomerIDException("Customer with ID " + customer.getCustomer_id() + " already exists");
	        }
	        Customers savedCustomer = customerService.addCustomer(customer);
	        model.addAttribute("message", "Customer created successfully with ID: " + savedCustomer.getCustomer_id());
	        return "addCustomer"; 
	    } catch (DuplicateCustomerIDException e) {
	        model.addAttribute("errorMessage", "Conflict: " + e.getMessage());
	        return "error"; 
	    }
	}
 
 
	@GetMapping("/{customer_id}/orders")
	public String getOrdersByCustomerId(@PathVariable int customer_id, Model model) {
	    try {
	        List<Orders> orders = customerService.getOrdersByCustomerId(customer_id);
	        model.addAttribute("orders", orders);
	        return "orders"; // This should match the name of your Thymeleaf template file for displaying orders
	    } catch (CustomerNotFoundException e) {
	        return "CustomerNotFoundException"; // You can create a separate Thymeleaf template for displaying a customer not found message
	    }
	}
 
	@DeleteMapping("/{customer_id}/")
	public String deleteCustomerById(@PathVariable int customer_id, Model model) {
	    Customers customer = customerService.getCustomerById(customer_id);
	    if (customer != null) {
	        model.addAttribute("customer", customer);
	        return "deleteConfirmation";
	    } else {
	        return "customerNotFound"; // You can create a separate Thymeleaf template for displaying a customer not found message
	    }
	}

	@GetMapping("/")
    public String home() {
        return "index"; // Assuming your homepage HTML file is named "index.html"
    }

	@GetMapping("/update/{customer_id}")
    public String showUpdateForm(@PathVariable("customer_id") int customerId, Model model) throws CustomerNotFoundException{
        // Fetch the customer from the database using customer ID
        Customers customer = customerService.getCustomerById(customerId);
        // Add the customer to the model
        model.addAttribute("customer", customer);
        return "updateCustomer"; // Return the HTML page for updating customer
    }
 
    // POST request to update the customer
    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("customer") Customers updatedCustomer) throws CustomerNotFoundException {
        // Update customer details using customerService
        customerService.updateCustomer(updatedCustomer);
        return "redirect:/customers/html"; // Redirect to customer list page after updating
    }

 
	    @GetMapping("/Customer.html")
	    public String showCustomersPage() {
	        return "Customer";
	    }
 
 
 
}

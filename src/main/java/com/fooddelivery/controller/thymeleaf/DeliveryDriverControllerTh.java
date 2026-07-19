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
import org.springframework.web.bind.annotation.RequestMapping;
import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.model.DeliveryDrivers;
import com.fooddelivery.service.DeliveryDriversService;
@RequestMapping("/api/drivers")
@Controller
public class DeliveryDriverControllerTh {
    
    @Autowired
    private DeliveryDriversService deliveryDriversService;
    
    @GetMapping("/")
    public String home(Model model) {
        // You can add any necessary data to the model here
        return "Home";
    }
    @GetMapping("/delivery_drivers")
    public String showDeliveryDriversPage() {
        return "delivery_drivers"; // This corresponds to the name of your Thymeleaf template file (delivery_drivers.html)
    }
    @GetMapping("/drivers")
    public String getAllDrivers(Model model) {
        List<DeliveryDrivers> drivers = deliveryDriversService.getAllDeliveryDrivers();
        model.addAttribute("drivers", drivers);
        return "deliverydrivers";
    }
    @GetMapping("/drivers/{id}")
    public String getDriverById(@PathVariable("id") int id, Model model) {
        DeliveryDrivers driver = deliveryDriversService.getDeliveryDriversById(id);
        model.addAttribute("driver", driver);
        return "getbyiddrivers";
    }
 
    @GetMapping("/update/{driver_id}")
    public String showUpdateForm(@PathVariable("driver_id") int driverId, Model model) throws InvalidDriverIDException {
        DeliveryDrivers driver = deliveryDriversService.getDeliveryDriversById(driverId);
        model.addAttribute("driver", driver);
        return "updateddrivers"; // Assuming your Thymeleaf template name is "update-delivery-driver"
    }
 
    @PostMapping("/update/{driver_id}") // Change the mapping to include driver id
    public String updateDeliveryDrivers(@PathVariable("driver_id") int driverId, @ModelAttribute("driver") DeliveryDrivers updatedDriver) throws InvalidDriverIDException {
        System.out.println("Entering updateDeliveryDrivers method...");
        updatedDriver.setDriver_id(driverId); // Set the driver id in the updated driver object
        deliveryDriversService.updateDeliveryDrivers(updatedDriver);
        System.out.println("Driver updated successfully.");
        return "redirect:/api/drivers/delivery_drivers"; // Redirect to the correct URL
    }
    
 
}
 
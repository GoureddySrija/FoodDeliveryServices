package com.fooddelivery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.DuplicateDriverIDException;
import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.Exception.NoSuchDriverIDException;
import com.fooddelivery.model.DeliveryDrivers;
import com.fooddelivery.service.DeliveryDriversService;

@RestController
@RequestMapping(value="/api/drivers")
public class DeliveryDriversController {
	@Autowired
	private DeliveryDriversService deliverydriversService;
	@GetMapping(consumes = "application/json", produces="application/json")
	public ResponseEntity<String> getAllDeliveryDrivers() {
        try {
            List<DeliveryDrivers> drivers = deliverydriversService.getAllDeliveryDrivers();
            return ResponseEntity.ok(drivers.toString());
        } catch (NoSuchDriverIDException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
 
	@GetMapping("/{driverId}")
	 public ResponseEntity<String> getDriverById(@PathVariable int driverId) {
        try {
            DeliveryDrivers driver = deliverydriversService.getDeliveryDriversById(driverId);
            return ResponseEntity.ok(driver.toString());
        } catch (InvalidDriverIDException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	@PutMapping("/{driver_id}")
	public ResponseEntity<DeliveryDrivers> updateDeliveryDrivers(@RequestBody DeliveryDrivers dd) throws InvalidDriverIDException{
		DeliveryDrivers deliverydrivers=deliverydriversService.updateDeliveryDrivers(dd);
		if(dd.getDriver_id()<=0) {
			throw new InvalidDriverIDException("Invalid Driver ID: "+dd.getDriver_id());
		}
		System.out.println("Driver details updated successfully");		
		return new ResponseEntity<DeliveryDrivers>( deliverydrivers,HttpStatus.ACCEPTED);
	}
}


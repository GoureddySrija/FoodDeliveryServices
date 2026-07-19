package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.CustomException;
import com.fooddelivery.Exception.DuplicateDriverIDException;
import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.Exception.NoSuchDriverIDException;
import com.fooddelivery.Repository.DeliveryDriversRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.model.DeliveryDrivers;
import com.fooddelivery.model.Orders;

import jakarta.transaction.Transactional;

@Service
public class DeliveryDriversServiceImpl implements DeliveryDriversService {
	@Autowired
	private DeliveryDriversRepository deliverydriversRepository;
	
	@Autowired
    OrdersRepository ordersRepository;
	public DeliveryDriversServiceImpl(DeliveryDriversRepository deliverdriversRepository) {
		this.deliverydriversRepository=deliverydriversRepository;
	}
 
	@Override
	public List<DeliveryDrivers> getAllDeliveryDrivers() throws NoSuchDriverIDException {
        List<DeliveryDrivers> drivers = deliverydriversRepository.findAll();
        if (drivers.isEmpty()) {
            throw new NoSuchDriverIDException("No drivers found.");
        }
        return drivers;
    }
 
	@Override
	public DeliveryDrivers getDeliveryDriversById(int driverId) throws InvalidDriverIDException {
        String driverIdString = String.valueOf(driverId);
        try {
            Optional<DeliveryDrivers> driverOptional = deliverydriversRepository.findById(driverId);
            if (driverOptional.isPresent()) {
                return driverOptional.get();
            } else {
                throw new InvalidDriverIDException("Driver with ID " + driverId + " not found.");
            }
            }catch (NumberFormatException e) {
                throw new InvalidDriverIDException("Invalid driver ID format: " + driverIdString);
            }
        }
 
	@Override
	@Transactional
	public DeliveryDrivers updateDeliveryDrivers(DeliveryDrivers deliverydrivers) throws InvalidDriverIDException {
	    Optional<DeliveryDrivers> optionalDrivers = deliverydriversRepository.findById(deliverydrivers.getDriver_id());
	    if (optionalDrivers.isPresent()) {
	        DeliveryDrivers existingDriver = optionalDrivers.get();
	        existingDriver.setDriver_name(deliverydrivers.getDriver_name());
	        existingDriver.setDriver_phone(deliverydrivers.getDriver_phone());
	        existingDriver.setDriver_vehicle(deliverydrivers.getDriver_vehicle());
	        return deliverydriversRepository.save(existingDriver);
	    } else {
	        throw new InvalidDriverIDException("Invalid Driver ID: " + deliverydrivers.getDriver_id());
	    }

	}
}

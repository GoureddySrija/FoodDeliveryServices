package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.CustomException;
import com.fooddelivery.model.DeliveryDrivers;
import com.fooddelivery.model.Orders;

public interface DeliveryDriversService {
	public List<DeliveryDrivers> getAllDeliveryDrivers();
	public DeliveryDrivers getDeliveryDriversById(int driverId);
	DeliveryDrivers updateDeliveryDrivers(DeliveryDrivers deliverydrivers);
}

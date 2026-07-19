package com.fooddelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="DeliveryDrivers")
public class DeliveryDrivers {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int driver_id ;
    private String driver_name;
    private String driver_phone;
    private String driver_vehicle;
    
	public DeliveryDrivers(int driver_id, String driver_name, String driver_phone, String driver_vehicle) {
		super();
		this.driver_id = driver_id;
		this.driver_name = driver_name;
		this.driver_phone = driver_phone;
		this.driver_vehicle = driver_vehicle;
	}
	
	public DeliveryDrivers() {

	}

	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	public String getDriver_phone() {
		return driver_phone;
	}
	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}
	public String getDriver_vehicle() {
		return driver_vehicle;
	}
	public void setDriver_vehicle(String driver_vehicle) {
		this.driver_vehicle = driver_vehicle;
	}
	@Override
	public String toString() {
		return "DeliveryDrivers [driver_id=" + driver_id + ", driver_name=" + driver_name + ", driver_phone="
				+ driver_phone + ", driver_vehicle=" + driver_vehicle + "]";
	}
    
}

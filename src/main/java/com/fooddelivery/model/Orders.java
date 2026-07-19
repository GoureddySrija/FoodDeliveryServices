package com.fooddelivery.model;

import java.time.LocalDateTime;
import java.util.Date;
import org.hibernate.annotations.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Orders")
public class Orders {
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int order_id; 
	
	@CreationTimestamp
    private LocalDateTime order_date;
    private String order_status;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customers customers;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="restaurant_id")
    private Restaurants restaurants;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="driver_id")
    private DeliveryDrivers deliveryDrivers;
    
    

	public Orders() {
		//super();
	}
	
	public Orders(int i, LocalDateTime now, Customers customers2, Restaurants restaurants2,
			DeliveryDrivers deliveryDrivers2, String string) {
		// TODO Auto-generated constructor stub
	}



	public Orders(int order_id, LocalDateTime order_date, String order_status, Customers customers,
			Restaurants restaurants, DeliveryDrivers deliveryDrivers) {
		super();
		this.order_id = order_id;
		this.order_date = order_date;
		this.order_status = order_status;
		this.customers = customers;
		this.restaurants = restaurants;
		this.deliveryDrivers = deliveryDrivers;
	}



	public int getOrder_id() {
		return order_id;
	}



	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}



	public LocalDateTime getOrder_date() {
		return order_date;
	}



	public void setOrder_date(LocalDateTime order_date) {
		this.order_date = order_date;
	}



	public String getOrder_status() {
		return order_status;
	}



	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}



	public Customers getCustomers() {
		return customers;
	}



	public void setCustomers(Customers customers) {
		this.customers = customers;
	}



	public Restaurants getRestaurants() {
		return restaurants;
	}



	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}



	public DeliveryDrivers getDeliveryDrivers() {
		return deliveryDrivers;
	}



	public void setDeliveryDrivers(DeliveryDrivers deliveryDrivers) {
		this.deliveryDrivers = deliveryDrivers;
	}



	@Override
	public String toString() {
		return "Orders [order_id=" + order_id + ", order_date=" + order_date + ", order_status=" + order_status
				+ ", customers=" + customers + ", restaurants=" + restaurants + ", deliveryDrivers=" + deliveryDrivers
				+ "]";
	}
    
	

}

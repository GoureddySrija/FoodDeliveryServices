package com.fooddelivery.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="MenuItems")
public class MenuItems {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int item_id;
    private String item_name;
    private String item_description;
    private float item_price;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="restaurant_id")
    private Restaurants restaurants;
    
	public MenuItems(int item_id, String item_name, String item_description, float item_price) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_description = item_description;
		this.item_price = item_price;
	}
	 public MenuItems() {
		 
	 }
//	public MenuItems(int i, String string1, String string2, double d) {
//	
//	}
    
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public float getItem_price() {
		return item_price;
	}
	public void setItem_price(float item_price) {
		this.item_price = item_price;
	}
	@Override
	public String toString() {
		return "MenuItems [item_id=" + item_id + ", item_name=" + item_name + ", item_description=" + item_description
				+ ", item_price=" + item_price +  "]";
	}

	public void setRestaurant_id(int restaurant_id) {
		// TODO Auto-generated method stub
		
	}
    
    
	

}

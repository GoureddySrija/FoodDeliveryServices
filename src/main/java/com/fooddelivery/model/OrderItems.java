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
@Table(name="OrderItems")
public class OrderItems {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int order_item_id;
    private int quantity;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Orders orders;
    
    @ManyToOne
    @JoinColumn(name="item_id")
    private MenuItems menuitems;
    
	public OrderItems(int order_item_id, int quantity) {
		super();
		this.order_item_id = order_item_id;
		this.quantity = quantity;
	}
	public OrderItems() {
	
	}
	public int getOrder_item_id() {
		return order_item_id;
	}
	public void setOrder_item_id(int order_item_id) {
		this.order_item_id = order_item_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "OrderItems [order_item_id=" + order_item_id + ", quantity=" + quantity + "]";
	}
	
    
}

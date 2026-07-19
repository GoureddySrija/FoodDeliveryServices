package com.fooddelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="Customers")
public class Customers {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int customer_id;
//    @NotBlank(message="Name is required")
//    @Size(max=20, message="Name cannot exceed 20 characters")
    private String customer_name;
    private String  customer_email;
    private String customer_phone;
	public Customers(int customer_id, String customer_name, String customer_email, String customer_phone) {
		super();
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.customer_email = customer_email;
		this.customer_phone = customer_phone;
	}
	
	public Customers() {
		
	}
    
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	@Override
	public String toString() {
		return "Customers [customer_id=" + customer_id + ", customer_name=" + customer_name + ", customer_email="
				+ customer_email + ", customer_phone=" + customer_phone + "]";
	}
    
}

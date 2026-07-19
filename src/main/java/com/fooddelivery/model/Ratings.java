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
@Table(name="Ratings")
public class Ratings {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int rating_id;
    private int rating;
    private String review;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name=" restaurant_id")
    private Restaurants restaurants;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Orders order;
    
	public Ratings(int rating_id, int rating, String review) {
		super();
		this.rating_id = rating_id;
		this.rating = rating;
		this.review = review;
	}
	public Ratings() {
		
	}
	public int getRating_id() {
		return rating_id;
	}
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	public Restaurants getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "Ratings [rating_id=" + rating_id + ", rating=" + rating + ", review=" + review + "]";
	}
    
}

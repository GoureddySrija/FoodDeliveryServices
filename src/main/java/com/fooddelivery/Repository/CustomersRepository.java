package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.fooddelivery.model.Customers;
 
@Repository
public interface CustomersRepository extends JpaRepository<Customers,Integer>{
}

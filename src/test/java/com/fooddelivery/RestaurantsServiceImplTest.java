package com.fooddelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.fooddelivery.Exception.CustomException;
import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.model.Restaurants;
import com.fooddelivery.service.RestaurantsServiceImpl;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestaurantsServiceImplTest {
	@Mock
	private RestaurantsRepository restaurantsRepository;

	@InjectMocks
	private RestaurantsServiceImpl restaurantsService;
	
	
	
	@Test
    public void testShowRestaurants() {
        List<Restaurants> restaurants = new ArrayList<>();
        restaurants.add(new Restaurants(53, "Bella Italia", "123 Main Street", "91234567"));
        restaurants.add(new Restaurants(54, "Tokyo Sushi Bar", "456 Elm Street", "91234897"));
        restaurants.add(new Restaurants(55, "Le Bistro Français", "789 Oak Avenue", "91234667"));
        when(restaurantsRepository.findAll()).thenReturn(restaurants);
        List<Restaurants> result = restaurantsService.getAllRestaurants();
        assertEquals(3, result.size());
        assertEquals("Bella Italia", result.get(0).getRestaurant_name());
        assertEquals("Tokyo Sushi Bar", result.get(1).getRestaurant_name());
        assertEquals("Le Bistro Français", result.get(2).getRestaurant_name());
        verify(restaurantsRepository).findAll();
    }
	
	@Test
    public void testAddRestaurants_DuplicateID() {
        // Arrange
        Restaurants existingRestaurant = new Restaurants();
        existingRestaurant.setRestaurant_id(123); // Assuming this ID already exists
        when(restaurantsRepository.existsById(existingRestaurant.getRestaurant_id())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateRestaurantIDException.class, () -> restaurantsService.addRestaurants(existingRestaurant));
    }
	
	@Test
    public void testGetAllRestaurants() {
        // Mock data
        Restaurants restaurant1 = new Restaurants(53, "Bella Italia", "123 Main Street", "91234567");
        Restaurants restaurant2 = new Restaurants(54, "Tokyo Sushi Bar", "456 Elm Street", "91234897");
        List<Restaurants> mockRestaurants = Arrays.asList(restaurant1, restaurant2);

        // Mock behavior of the repository
        when(restaurantsRepository.findAll()).thenReturn(mockRestaurants);

        // Call the method to be tested
        List<Restaurants> result = restaurantsService.getAllRestaurants();

        // Verify that the repository method was called
        verify(restaurantsRepository).findAll();

        // Assert that the result matches the mocked data
        assertEquals(mockRestaurants, result);
    }
	
	@Test
    @Transactional
    public void testUpdateRestaurants_ValidId() throws InvalidRestaurantIdException {
        // Mocking the previous state of the restaurant
		// Given
        Restaurants existingRes = new Restaurants(53, "Bella Italia", "123 Main Street", "+91234567");
        Restaurants updatedRes = new Restaurants(53, "Tokyo Sushi Bar", "456 Elm Street", "+91234897");
        // Arrange mock to return the existing restaurant when findById is called
        when(restaurantsRepository.findById(53)).thenReturn(Optional.of(existingRes));
        // Arrange mock to return the updated restaurant when save is called
        when(restaurantsRepository.save(existingRes)).thenReturn(updatedRes);
        // When
        Restaurants result = restaurantsService.updateRestaurants(updatedRes);
 
        // Then
        // Verify that findById method is called with the provided ID
        verify(restaurantsRepository).findById(53);
        // Verify that save method is called with the updated restaurant
        verify(restaurantsRepository).save(existingRes);
        // Assert that the returned restaurant object is the same as the updatedRes
        assertEquals(updatedRes, result);
    }

	@Test
    @Transactional
    public void testUpdateRestaurants_InvalidId() {
        // Mocking the scenario where findById() returns an empty optional
        when(restaurantsRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Creating an updated restaurant with an invalid ID
        Restaurants updatedRestaurant = new Restaurants();
        updatedRestaurant.setRestaurant_id(999); // Non-existent restaurant ID
        updatedRestaurant.setRestaurant_name("New Name");
        updatedRestaurant.setRestaurant_address("New Address");
        updatedRestaurant.setRestaurant_phone("New Phone");

        // Calling the method under test and expecting an InvalidRestaurantIdException
        assertThrows(InvalidRestaurantIdException.class, () -> restaurantsService.updateRestaurants(updatedRestaurant));

        // Verifying that the repository's findById method was called with the correct ID
        verify(restaurantsRepository).findById(updatedRestaurant.getRestaurant_id());

        // Verifying that no other interactions occurred with the repository
        verifyNoMoreInteractions(restaurantsRepository);
    }
	
	@Before(value = "")
    public void setUp() {
        // Mock behavior of restaurantsRepository here if needed
    }
 
	@Test
    public void testMethodThatAffectsState_Exists() throws NoSuchRestaurantIDException {
        // Arrange
        int existingRestaurantId = 1;
        Restaurants existingRestaurant = new Restaurants(existingRestaurantId, "Restaurant Name", null, null);
 
        // Stubbing the behavior of the repository
        lenient().when(restaurantsRepository.findById(existingRestaurantId)).thenReturn(Optional.of(existingRestaurant));
 
        // Act
        try {
            restaurantsService.getRestaurantsById(existingRestaurantId);
 
            // Verify that getRestaurantsById method is called with the correct ID
            verify(restaurantsRepository).findById(existingRestaurantId);
        } catch (NoSuchRestaurantIDException e) {
            // Handle exception if necessary
        }
    }
 
 
    // Negative Test Case
    @Test
    public void testGetRestaurantsById_NotExists() {
        // Arrange
        int nonExistingRestaurantId = 999; // Assuming this ID does not exist in the repository
 
        // Stubbing the behavior of the repository
        lenient().when(restaurantsRepository.findById(nonExistingRestaurantId)).thenThrow(new NoSuchRestaurantIDException("Restaurant ID: " + nonExistingRestaurantId + " not found"));
 
        // Act and Assert
        assertThrows(NoSuchRestaurantIDException.class, () -> restaurantsService.getRestaurantsById(nonExistingRestaurantId));
    }
 
@Test
public void deleteRestaurantsById_ValidId_RestaurantDeleted() throws  InvalidRestaurantIdException{
    int restaurantId = -1;
    lenient().when(restaurantsRepository.existsById(restaurantId)).thenReturn(false);
    InvalidRestaurantIdException exception=assertThrows(InvalidRestaurantIdException.class,
    		()->restaurantsService.deleteRestaurantsById(restaurantId));
    assertEquals("Invalid Restaurant ID: -1",exception.getMessage());
}
 
@Test
void deleteRestaurantsById_InvalidId_ExceptionThrown() {
    int restaurantId = 1;
    lenient().when(restaurantsRepository.existsById(restaurantId)).thenReturn(false);
    try {
    	restaurantsService.deleteRestaurantsById(restaurantId);
    	fail("Excepted InvalidRestaurantId Exception to be thrown ");
    }
    catch(InvalidRestaurantIdException e) {
    	assertEquals("Invalid Restaurant ID: "+restaurantId,e.getMessage());
    }
    verify(restaurantsRepository, never()).deleteById(anyInt());
}
    
    
	}
   

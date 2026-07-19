package com.fooddelivery;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
 
import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.Repository.DeliveryDriversRepository;
import com.fooddelivery.model.DeliveryDrivers;
import com.fooddelivery.service.DeliveryDriversServiceImpl;

import jakarta.transaction.Transactional;
 
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeliveryDriversServiceImplTest {
	@Mock
	private DeliveryDriversRepository deliverydriversRepository;
	@InjectMocks
	private DeliveryDriversServiceImpl deliverydriversService;

	@Test
    public void testShowDeliveryDrivers() {
        // Arrange
        List<DeliveryDrivers> deliverydrivers = new ArrayList<>();
        deliverydrivers.add(new DeliveryDrivers(51, "Jane Kalix", "+112233445151", "Bike"));
        deliverydrivers.add(new DeliveryDrivers(52, "Mahesh Babu", "+112233445252", "Scooty"));
        deliverydrivers.add(new DeliveryDrivers(53, "John Leo", "+112233445353", "Car"));
        when(deliverydriversRepository.findAll()).thenReturn(deliverydrivers);
        // Act
        List<DeliveryDrivers> result = deliverydriversService.getAllDeliveryDrivers();
        // Assert
        assertEquals(3, result.size());
        assertEquals(51, result.get(0).getDriver_id());
        assertEquals(52, result.get(1).getDriver_id());
        assertEquals(53, result.get(2).getDriver_id());
        verify(deliverydriversRepository).findAll();
    }
	@Test
    public void testGetDeliveryDriversById() throws InvalidDriverIDException {
		int driverId = 51; // Correct driverId to match the existing driver
	    DeliveryDrivers existingDriver = new DeliveryDrivers(50, "Emily Smith", "+112233445050", "Scooter");
	    // Arrange mock to return the existing driver when findById is called with driverId
	    when(deliverydriversRepository.findById(driverId)).thenReturn(Optional.of(existingDriver));
 
	    // When
	    DeliveryDrivers result = deliverydriversService.getDeliveryDriversById(driverId);
 
	    // Then
	    // Verify that findById method is called with the provided ID
	    verify(deliverydriversRepository).findById(driverId);
	    // Assert that the returned driver object is the same as the existingDriver
	    assertEquals(existingDriver, result);
	}
	@Test
	public void testGetDeliveryDriversByIdInvalidId() {
		int driverId=-1;
		when(deliverydriversRepository.findById(driverId)).thenReturn(Optional.empty());
		Exception exception =assertThrows(InvalidDriverIDException.class,()->{
			deliverydriversService.getDeliveryDriversById(driverId);
		});
		String expectedMessage=String.valueOf(driverId);
		String actualMessage=exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		verify(deliverydriversRepository,times(1)).findById(driverId);
		verify(deliverydriversRepository,never()).deleteById(anyInt());
	}
	@Test
    public void testUpdateDeliveryDrivers() throws InvalidDriverIDException {
        // Mocking the previous state of the restaurant
		// Given
        DeliveryDrivers existingDriver = new DeliveryDrivers(50, "Emily Smith", "+112233445050", "Scooter");
        DeliveryDrivers updatedDriver = new DeliveryDrivers(51, "Emily Smith", "+112233445050", "Scooter");
        // Arrange mock to return the existing restaurant when findById is called
        when(deliverydriversRepository.findById(51)).thenReturn(Optional.of(existingDriver));
        // Arrange mock to return the updated restaurant when save is called
        when(deliverydriversRepository.save(existingDriver)).thenReturn(updatedDriver);
        // When
        DeliveryDrivers result = deliverydriversService.updateDeliveryDrivers(updatedDriver);
        // Then
        // Verify that findById method is called with the provided ID
        verify(deliverydriversRepository).findById(51);
        // Verify that save method is called with the updated restaurant
        verify(deliverydriversRepository).save(existingDriver);
        // Assert that the returned restaurant object is the same as the updatedRes
        assertEquals(updatedDriver, result);
    }
	@Test
    public void testUpdateDeliveryDrivers_InvalidId() {
        // Mocking the scenario where findById() returns an empty optional
        when(deliverydriversRepository.findById(anyInt())).thenReturn(Optional.empty());
        // Creating an updated restaurant with an invalid ID
        DeliveryDrivers updatedDriver = new DeliveryDrivers();
        updatedDriver.setDriver_id(999); // Non-existent restaurant ID
        updatedDriver.setDriver_name("New Name");
        updatedDriver.setDriver_phone("New Address");
        updatedDriver.setDriver_vehicle("New Phone");
        // Calling the method under test and expecting an InvalidRestaurantIdException
        assertThrows(InvalidDriverIDException.class, () -> deliverydriversService.updateDeliveryDrivers(updatedDriver));
        // Verifying that the repository's findById method was called with the correct ID
        verify(deliverydriversRepository).findById(updatedDriver.getDriver_id());
        // Verifying that no other interactions occurred with the repository
        verifyNoMoreInteractions(deliverydriversRepository);
    }   
}
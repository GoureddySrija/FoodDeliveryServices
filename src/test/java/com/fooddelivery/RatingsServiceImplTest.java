package com.fooddelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Repository.RatingsRepository;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.model.Ratings;
import com.fooddelivery.service.RatingsServiceImpl;
import com.fooddelivery.service.RestaurantsServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingsServiceImplTest {
	@Mock
	private RatingsRepository ratingsRepository;

	@InjectMocks
	private RatingsServiceImpl ratingsService;
	
    @Test
    public void testGetAllRatingsByRestaurantId_PositiveScenario() throws InvalidRestaurantIdException {
        // Mocking the scenario where ratings are found for the restaurant ID
        int restaurantId = 1; // Example restaurant ID
        List<Ratings> mockRatings = new ArrayList<>();
        mockRatings.add(new Ratings());
        mockRatings.add(new Ratings());

        when(ratingsRepository.findByRestaurantId(restaurantId)).thenReturn(mockRatings);

        // Calling the method under test
        List<Ratings> result = ratingsService.getAllRatingsByRestaurantId(restaurantId);

        // Verifying that the ratingsRepository's findByRestaurantId method was called with the correct ID
        verify(ratingsRepository).findByRestaurantId(restaurantId);

        // Asserting that the returned list is not empty
        assertFalse(result.isEmpty());
        // Asserting that the returned list contains the same elements as the mockRatings
        assertEquals(mockRatings, result);
    }
    
    @Test
    public void testGetAllRatingsByRestaurantId_NegativeScenario() {
        // Mocking the scenario where no ratings are found for the restaurant ID
        int restaurantId = 1; // Example restaurant ID
        List<Ratings> mockRatings = new ArrayList<>(); // Empty list

        when(ratingsRepository.findByRestaurantId(restaurantId)).thenReturn(mockRatings);

        // Calling the method under test and expecting an InvalidRestaurantIdException
        assertThrows(InvalidRestaurantIdException.class, () -> ratingsService.getAllRatingsByRestaurantId(restaurantId));

        // Verifying that the ratingsRepository's findByRestaurantId method was called with the correct ID
        verify(ratingsRepository).findByRestaurantId(restaurantId);
    }

}

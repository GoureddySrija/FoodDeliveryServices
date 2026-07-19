package com.fooddelivery;
 
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
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

import com.fooddelivery.Exception.InvalidItemIdException;
import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Repository.MenuItemsRepository;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.model.MenuItems;
import com.fooddelivery.model.Restaurants;
import com.fooddelivery.service.MenuItemsServiceImpl;
 
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MenuItemsServiceImplTest {
	@Mock
	private MenuItemsRepository menuItemsRepository;
	@Mock
	private RestaurantsRepository restaurantsRepository;
	@InjectMocks
    private MenuItemsServiceImpl menuItemsService;
	//GET ALL MENUITEMS
	@Test
    public void testGetmenuItemsByRestaurantId() {
        int restaurantId = 23; // Example restaurant ID
        List<MenuItems> mockMenuItems = createMockMenuItems(); // Create mock menu items
        when(menuItemsRepository.findByRestaurantId(restaurantId)).thenReturn(mockMenuItems);
        List<MenuItems> result = menuItemsService.getMenuItemsByRestaurantId(restaurantId);
        assertNotNull(result);
        assertEquals(mockMenuItems.size(), result.size());
        verify(menuItemsRepository).findByRestaurantId(restaurantId);
    }
    private List<MenuItems> createMockMenuItems() {
    	List<MenuItems> mockMenuItems = new ArrayList<>();
    	mockMenuItems.add(new MenuItems(54, "Chocolate Ice Cream", "Icecream filled with toppings", 7.99f));
    	mockMenuItems.add(new MenuItems(55, "Choco Lava", "Delicious cake filled with chocolate", 9.99f));
    	mockMenuItems.add(new MenuItems(56, "Mango Ice Cream", "Icecream with Seasonal fruit and toppings", 6.99f));
    	return mockMenuItems;
    }
 
    // ADD MENU ITEMS POSITIVE
    @Test
    public void testAddmenuItems_WithValidRestaurantId() throws InvalidRestaurantIdException { 
    	int validRestaurantId = 37;
        MenuItems mockMenuItems = new MenuItems(validRestaurantId, null, null, validRestaurantId);
        mockMenuItems.setItem_id(4);
        mockMenuItems.setItem_name("Burger");
        mockMenuItems.setItem_description("Delicious burger with cheese and bacon"); 
        mockMenuItems.setItem_price(9.99f);
        when(restaurantsRepository.findById(validRestaurantId)).thenReturn(Optional.of(new Restaurants()));
        when(menuItemsRepository.save(mockMenuItems)).thenReturn(mockMenuItems);// Stubbing the behavior of menuItemsRepository
        MenuItems result = menuItemsService.addmenuItems(validRestaurantId, mockMenuItems);// Calling the method to be tested
 
        assertNotNull(result);
        assertEquals("Burger", result.getItem_name()); 
        assertEquals("Delicious burger with cheese and bacon", result.getItem_description()); // Asserting description
        assertEquals(9.99f, result.getItem_price(), 0.01);
        verify(restaurantsRepository).findById(validRestaurantId);
        verify(menuItemsRepository).save(mockMenuItems);
    }
   // ADD MENU ITEMS NEGATIVE
    @Test
    public void testAddmenuItems_WithInvalidRestaurantId() { 
        int invalidRestaurantId = 45;
        MenuItems mockMenuItems = createMockMenuItems1();
        when(restaurantsRepository.findById(invalidRestaurantId)).thenReturn(Optional.empty());// Stubbing the behavior of restaurantRepository
        InvalidRestaurantIdException exception = assertThrows(InvalidRestaurantIdException.class, () -> {
            menuItemsService.addmenuItems(invalidRestaurantId, mockMenuItems);
        });
        assertEquals("Restaurant Id is not found with Id: " + invalidRestaurantId, exception.getMessage());
        verify(restaurantsRepository).findById(invalidRestaurantId);
        verify(menuItemsRepository, never()).save(any(MenuItems.class));
    }
    private MenuItems createMockMenuItems1() {
    	MenuItems items = new MenuItems(57,"Chocolate Shake","Chocolate Shake with ice cream",12f);
        return items;
    }
    //UPDATE MENUITEMS POSITIVE
    @Test
    public void testUpdatemenuItems_Postive() throws InvalidMenuItemException { 
        int restaurantId = 25; 
        int itemId = 53; 
        MenuItems mockMenuItems = new MenuItems(itemId, null, null, itemId);
        mockMenuItems.setItem_name("Burger"); 
        mockMenuItems.setItem_description("Delicious burger with cheese and bacon"); 
        mockMenuItems.setItem_price(9.99f);
 
        when(menuItemsRepository.findByIdAndRestaurantId(itemId, restaurantId)).thenReturn(mockMenuItems);
        when(menuItemsRepository.save(any(MenuItems.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the argument passed to save
        MenuItems updatedMenuItems = new MenuItems(itemId, null, null, itemId);
        updatedMenuItems.setItem_name("Double Cheeseburger"); 
        updatedMenuItems.setItem_description("Double cheeseburger with extra toppings"); 
        updatedMenuItems.setItem_price(12.99f); 
        MenuItems result = menuItemsService.updatemenuItems(restaurantId, itemId, updatedMenuItems);
        assertNotNull(result);
        assertEquals("Double Cheeseburger", result.getItem_name());
        assertEquals("Double cheeseburger with extra toppings", result.getItem_description()); 
        assertEquals(12.99f, result.getItem_price(), 0.01); 
        verify(menuItemsRepository).findByIdAndRestaurantId(itemId, restaurantId);
        verify(menuItemsRepository).save(mockMenuItems);
    }
    @Test
    public void testUpdatemenuItems_InvalidMenuItemPrice() { 
        int restaurantId = 32; 
        int itemId = 21; 
        MenuItems mockMenuItems = new MenuItems(itemId, null, null, itemId);
        mockMenuItems.setItem_name("Burger");
        mockMenuItems.setItem_price(-5.0f); 
        assertThrows(InvalidMenuItemException.class,()->{
        	menuItemsService.updatemenuItems(restaurantId,itemId,mockMenuItems);
        });
        verify(menuItemsRepository, never()).findByIdAndRestaurantId(itemId, restaurantId);
 
    }

  //DELETE MENUITEMS  POSITIVE
    @Test
    public void testDeletemenuItems_ValidItemId() throws InvalidItemIdException {
        int restaurantId = 53; 
        int itemId = 23 ;
        when(restaurantsRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurants()));
 
        when(menuItemsRepository.findById(itemId)).thenReturn(Optional.of(new MenuItems(itemId, null, null, itemId)));
        assertDoesNotThrow(() -> menuItemsService.deletemenuItems(restaurantId, itemId));
        verify(restaurantsRepository).findById(restaurantId);
        verify(menuItemsRepository).findById(itemId);
        verify(menuItemsRepository).deleteByRestaurantIdAndItemId(restaurantId, itemId);
    }
  //DELETE MENUITEMS  NEGATIVE
    @Test
    public void testDeletemenuItems_InvalidItemId() {
 
        int restaurantId = 47; 
        int itemId = 63; 
        when(restaurantsRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurants()));
 
        when(menuItemsRepository.findById(itemId)).thenReturn(Optional.empty());
        InvalidItemIdException exception = assertThrows(InvalidItemIdException.class, () -> {
            menuItemsService.deletemenuItems(restaurantId, itemId);
        });
        assertEquals("Item id is not found", exception.getMessage());
        verify(restaurantsRepository).findById(restaurantId);
        verify(menuItemsRepository).findById(itemId);
        verify(menuItemsRepository, never()).deleteByRestaurantIdAndItemId(anyInt(), anyInt());
    }
 
}

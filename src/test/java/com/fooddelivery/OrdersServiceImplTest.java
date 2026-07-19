
package com.fooddelivery;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.model.Customers;
import com.fooddelivery.model.DeliveryDrivers;
import com.fooddelivery.model.Orders;
import com.fooddelivery.model.Restaurants;
import com.fooddelivery.service.OrdersServiceImpl;
 
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OrdersServiceImplTest {
	@Mock
	private OrdersRepository ordersRepository;
	@InjectMocks
	private OrdersServiceImpl ordersService;
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	//POSITIVE TEST==>PLACEORDER
	@Test
	public void testPlaceOrder() throws DuplicateOrderIdException{
		Orders orders1=new Orders(1,LocalDateTime.now(),new Customers(),new Restaurants(),new DeliveryDrivers(),"pending");
		
		when(ordersRepository.findById(orders1.getOrder_id())).thenReturn(Optional.empty());
		when(ordersRepository.save(orders1)).thenReturn(orders1);
		
		Orders result1=ordersService.placeOrder(orders1);
		
		assertEquals(orders1,result1);
	
		verify(ordersRepository,times(1)).findById(orders1.getOrder_id());
		verify(ordersRepository,times(1)).save(orders1);
		
	}
	//NEGATIVE TEST==>PLACEORDER
	@Test
	public void testPlaceOrderWithDuplicateOrderId() {

		
		Orders existingOrder=new Orders();
		existingOrder.setOrder_id(1);
		when(ordersRepository.findById(1)).thenReturn(Optional.of(existingOrder));
		
		Orders orders=new Orders();
		orders.setOrder_id(1);
		assertThrows(DuplicateOrderIdException.class,()->{
			ordersService.placeOrder(orders);
		});
		
	}

	@Test
	public void testGetOrdersById() throws OrdersNotFoundException , InvalidOrderIdException{
		Orders orders1=new Orders(3,LocalDateTime.now(),new Customers(),new Restaurants(),new DeliveryDrivers(),"pending");
		when(ordersRepository.findById(orders1.getOrder_id())).thenReturn(Optional.of(orders1));
		Orders result =ordersService.getOrders(orders1.getOrder_id());
		assertThat(result,is(orders1));
		verify(ordersRepository,times(1)).findById(orders1.getOrder_id());	
	}
	@Test
	public void testGetOrdersByIdNotFound() {
		int existingOrderId=1;
		lenient().when(ordersRepository.findById(existingOrderId)).thenReturn(Optional.empty());
		assertThrows(OrdersNotFoundException.class,()->{
			ordersService.getOrders(existingOrderId);
		});
		verify(ordersRepository,times(1)).findById(existingOrderId);
	}
	
	@Test
	public void testUpdatedOrderStatusInvalidOrderId() {
		assertThrows(InvalidOrderIdException.class, ()->{
			ordersService.UpdateOrderStatus(-1, "Delivered");
		});
	}
	@Test
	public void testUpdatedOrderStatusOrdersNotFound() {
		assertThrows(OrdersNotFoundException.class, ()->{
			ordersService.UpdateOrderStatus(999,"Delivered");
		});
	}
	@Test
	public void testCancelOrder() throws InvalidOrderIdException {
		int orderId=1;
		Orders existingOrder=new Orders(orderId,LocalDateTime.now(),new Customers(),new Restaurants(),new DeliveryDrivers(),"Pending");
		when(ordersRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
		boolean canceled=ordersService.cancelOrder(orderId);
		assertTrue(canceled);
		verify(ordersRepository,times(1)).findById(orderId);
		verify(ordersRepository,times(1)).deleteById(orderId);
	}
	@Test
	public void testCancelOrderInvalidOrderId() {
		int orderId=-1;
		when(ordersRepository.findById(orderId)).thenReturn(Optional.empty());
		Exception exception =assertThrows(InvalidOrderIdException.class,()->{
			ordersService.cancelOrder(orderId);
		});
		String expectedMessage="Order Id with "+orderId+" is not present in the database ";
		String actualMessage=exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		verify(ordersRepository,times(1)).findById(orderId);
		verify(ordersRepository,never()).deleteById(anyInt());
	}


}


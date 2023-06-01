package com.example.Restaurant.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.onlinefoodapplication.entities.Address;
import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.FoodCart;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.OrderDetail;
import com.example.onlinefoodapplication.entities.OrderedItems;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoOrderPlacedByCustomer;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchOrder;
import com.example.onlinefoodapplication.models.*;
import com.example.onlinefoodapplication.repo.CustomerRepository;
import com.example.onlinefoodapplication.repo.OrderRepository;
import com.example.onlinefoodapplication.repo.RestaurantRepository;
import com.example.onlinefoodapplication.service.OrderServiceImpl;

class OrderServiceImplTest {

	@InjectMocks
	OrderServiceImpl service;

	@Mock
	private CustomerRepository cust_repo;

	@Mock
	private RestaurantRepository rest_repo;

	@Mock
	private OrderRepository repo;

	Customer cust = new Customer();
	OrderDetail order = new OrderDetail();

	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		cust.setCustomerId(1);
		cust.setFirstName("nishad");
		cust.setLastName("kadam");
		cust.setGender("male");
		cust.setEmail("yoo");
		cust.setMobileNumber("678");
		cust.setActive(true);
		cust.setAge(20);
		cust.setAddress(new Address(1, "yoo", 56, "yy", "jj", "jksd", "uu", "gu", "78"));
		cust.setCart(new FoodCart(1,
				new ArrayList<Item>(Arrays.asList(new Item(5, "samosa", 50), new Item(6, "puri", 50)))));

		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime time = LocalDateTime.now();

		order.setOrder_id(1);
		order.setActive(false);
		order.setCustomer_id(1);
		order.setOrderDateAndTime(time.format(formatter));
		order.setTotalAmount(100);
		order.setTotalItems(2);
		order.setOrderStatus("NOT_DELIVERED");
		order.setOrderdItem(new ArrayList<OrderedItems>(
				Arrays.asList(new OrderedItems(1, 5, "samosa", 50), new OrderedItems(2, 6, "puri", 50))));
		order.setCart_id(1);
	}

	@Test
	void testAddOrder() throws CustomerNotPresent, CartIsEmpty {
		Customer temp = null;
		when(cust_repo.findById(1)).thenReturn(Optional.ofNullable(temp));

		assertThrows(CustomerNotPresent.class, () -> service.addOrder(1));

	}
	@Test
	void testAddOrder2() throws CustomerNotPresent, CartIsEmpty {
		Customer cust = new Customer();
		cust.setCustomerId(1);
		cust.setFirstName("nishad");
		cust.setLastName("kadam");
		cust.setGender("male");
		cust.setEmail("yoo");
		cust.setMobileNumber("678");
		cust.setActive(true);
		cust.setAge(20);
		cust.setAddress(new Address(1, "yoo", 56, "yy", "jj", "jksd", "uu", "gu", "78"));
		cust.setCart(new FoodCart(1,
				new ArrayList<Item>()));
		when(cust_repo.findById(1)).thenReturn(Optional.ofNullable(cust));

		assertThrows(CartIsEmpty.class, () -> service.addOrder(1));

	}
	
	@Test
	void testAddOrder3() throws CustomerNotPresent, CartIsEmpty {
		Customer cust=new Customer();
		cust.setCustomerId(1);
		cust.setFirstName("nishad");
		cust.setLastName("kadam");
		cust.setGender("male");
		cust.setEmail("yoo");
		cust.setMobileNumber("678");
		cust.setCart(new FoodCart(1, new ArrayList<Item>(Arrays.asList(new Item(5, "samosa", 50), new Item(6, "puri", 50)))));
		cust.setActive(true);
		cust.setAge(20);
		cust.setAddress(new Address(1,"yoo",56,"yy","jj","jksd","uu","gu","78"));
		
		
		OrderDetail order =new OrderDetail();
		// generating date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime time = LocalDateTime.now();
		
	    order.setOrder_id(1);
	    order.setActive(false);
	    order.setCustomer_id(1);
	    order.setOrderDateAndTime(time.format(formatter));
	    order.setTotalAmount(100);
	    order.setTotalItems(2);
	    order.setOrderStatus("NOT_DELIVERED");
	    order.setOrderdItem(new ArrayList<OrderedItems>(Arrays.asList(new OrderedItems(1, 5, "samosa", 50),new OrderedItems(2, 6, "puri", 50))));
	    order.setCart_id(1);
	    
	    
	    when(cust_repo.findById(1)).thenReturn(Optional.ofNullable(cust));
		
	    when(repo.save(ArgumentMatchers.any(OrderDetail.class))).thenReturn(order);
	    
	    assertEquals(order, service.addOrder(cust.getCustomerId()));
		
	}
	
	
	@Test
	void cancelOrderTest() {
		OrderDetail detail=null;
		when(repo.findById(1)).thenReturn(Optional.ofNullable(detail));
		assertThrows(NoSuchOrder.class , ()-> service.cancelOrder(1));
	
	}
//	
	@Test
	void cancelOrderTest2() throws NoSuchOrder {
		
		when(repo.findById(1)).thenReturn(Optional.ofNullable(order));
		assertEquals(0, service.cancelOrder(1));
	}
//	
	@Test
	void getOrderByCustomerIdTest() {
		
		when(repo.findAll()).thenReturn(new ArrayList<OrderDetail>());
		assertThrows(NoOrderPlacedByCustomer.class, ()->service.getOrderByCustomerId(1));	
	}
//	
	@Test
	void getOrderByCustomerIdTest2() {
		
		when(repo.findAll()).thenReturn(new ArrayList<OrderDetail>(Arrays.asList(new OrderDetail[] {order})));
		
		assertThrows(NoOrderPlacedByCustomer.class, ()->service.getOrderByCustomerId(3));	
	}
	
	@Test
	void getOrderByCustomerIdTest3() throws NoOrderPlacedByCustomer {
		when(repo.findAll()).thenReturn(new ArrayList<OrderDetail>(Arrays.asList(new OrderDetail[] {order})));
		assertEquals(
				new ArrayList<CustomerOrderView>(Arrays.asList(new CustomerOrderView(1, new ArrayList<OrderedItems>(Arrays.asList(new OrderedItems[] {new OrderedItems(1, 5, "samosa", 50), new OrderedItems(2, 6, "puri", 50)})))))
				, service.getOrderByCustomerId(1));
	
	}
	
	@Test
	void getOrdersForARestaurantTest() {
		List<OrderDetail> list=new ArrayList<OrderDetail>();
		when(repo.findAll()).thenReturn(list);
		
		assertThrows(NoOrderPlacedByCustomer.class, ()->service.getOrdersForARestaurant(1));
		
	}
	@Test
	void getOrdersForARestaurantTest2() throws NoOrderPlacedByCustomer {
		Restaurant rest=new Restaurant();
		rest.setName("leela");
		rest.setRest_id(1);
		rest.setContactNumber("12344");
		rest.setItems(new ArrayList<Item>(Arrays.asList(new Item[] {new Item(5, "samosa", 50), new Item(6, "puri", 50)})));		
	
		when(repo.findAll()).thenReturn(new ArrayList<OrderDetail>(Arrays.asList(new OrderDetail[] {order})));	
	
		when(rest_repo.findAll()).thenReturn(new ArrayList<Restaurant>(Arrays.asList(rest)));
		assertEquals(
				
				new ArrayList<RestaurantOrderView>(
						Arrays.asList(new RestaurantOrderView[] {
								new RestaurantOrderView(1,"leela",new ArrayList<OrderedItems>(
										Arrays.asList(new OrderedItems(1, 5, "samosa", 50), new OrderedItems(2, 6, "puri", 50))) )}))
				, service.getOrdersForARestaurant(1));
		
		
	}
//	

}

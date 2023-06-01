package com.example.Restaurant.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.example.onlinefoodapplication.controller.OrderController;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.OrderDetail;
import com.example.onlinefoodapplication.entities.OrderedItems;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoOrderPlacedByCustomer;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchOrder;
import com.example.onlinefoodapplication.exceptionhandler.NotLoggedIn;
import com.example.onlinefoodapplication.models.AddingOrder;
import com.example.onlinefoodapplication.models.CustomerOrderView;
import com.example.onlinefoodapplication.models.RestaurantOrderView;
import com.example.onlinefoodapplication.service.LoginService;
import com.example.onlinefoodapplication.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
	@InjectMocks
	OrderController controller;
	
	
	@Mock
	private OrderService service;
	
	@Mock
	private LoginService loginService;
	
	MockHttpServletRequest request;

	OrderDetail order;
	@BeforeEach
	void setUp() throws Exception {
		request = new MockHttpServletRequest();	
		
		order=new OrderDetail();
		order.setOrder_id(1);
		order.setActive(true);
		order.setCart_id(1);
		order.setCustomer_id(1);
		order.setTotalAmount(500);
		order.setOrderDateAndTime("12/03/04 22:45");
		order.setTotalItems(2);
		
		List<OrderedItems> list=new ArrayList<OrderedItems>();
		list.add(new OrderedItems(1,20,"samosa",70));
		order.setOrderdItem(list);
		
	}
	
	
	@Test
	@DisplayName("add order")
	void testAddOrder() {
		when(loginService.checkSession(request)).thenReturn(false);
		assertThrows(NotLoggedIn.class, ()->controller.addOrder(new AddingOrder(1), request));
	}
	@Test
	@DisplayName("add order 2")
	void testAddOrder2() throws CustomerNotPresent, CartIsEmpty, NotLoggedIn {
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.addOrder(1)).thenReturn(order);
		
		assertEquals(new ResponseEntity<OrderDetail>(order, HttpStatus.OK), controller.addOrder(new AddingOrder(1), request));
	}
	@Test
	@DisplayName("add order 3")
	void testAddOrder3() throws CustomerNotPresent, CartIsEmpty, NotLoggedIn {
		OrderDetail order=null;
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.addOrder(1)).thenReturn(null);
		
		assertEquals(new ResponseEntity<OrderDetail>(order,HttpStatus.INTERNAL_SERVER_ERROR), controller.addOrder(new AddingOrder(1), request));
	}
	
	@Test
	@DisplayName("Cancel order")
	void testCancelOrder() throws CustomerNotPresent, CartIsEmpty, NotLoggedIn, NoSuchOrder {
	
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.cancelOrder(1)).thenReturn(0);
		
		assertEquals(new ResponseEntity<String>("Order Cancelled",HttpStatus.OK), controller.cancelOrder(1, request));
	}
	
	@Test
	@DisplayName("Get order By CustomerId")
	void testGetOrdersByCustomerId() throws NoOrderPlacedByCustomer, NotLoggedIn {
		CustomerOrderView view =new CustomerOrderView();
		view.setId(1);
		List<OrderedItems> list=new ArrayList<OrderedItems>();
		list.add(new OrderedItems(1,20,"samosa",70));
		view.setList(list);
		
		List<CustomerOrderView> view2=new ArrayList<CustomerOrderView>();
		view2.add(view);
	
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getOrderByCustomerId(1)).thenReturn(view2);
		
		assertEquals(new ResponseEntity<List<CustomerOrderView>>(view2, HttpStatus.OK), controller.getOrdersByCustomerId(1, request));
//)
	}
	
	@Test
	@DisplayName("Get order of a restaurant")
	void testGetOrderOfARestaurant() throws NoOrderPlacedByCustomer, NotLoggedIn {
		RestaurantOrderView view =new RestaurantOrderView();
		view.setId(1);
		view.setRest_name("leela");
		List<OrderedItems> list=new ArrayList<OrderedItems>();
		list.add(new OrderedItems(1,20,"samosa",70));
		view.setOrderedlist(list);
		
		List<RestaurantOrderView> view2=new ArrayList<RestaurantOrderView>();
		view2.add(view);
		
		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.getOrdersForARestaurant(1)).thenReturn(view2);
		assertEquals(new ResponseEntity<List<RestaurantOrderView>>(view2, HttpStatus.OK), controller.getOrderOfARestaurant(1, request));

		
		
		
		
	}
	
}

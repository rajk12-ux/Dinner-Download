package com.example.Restaurant.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.example.onlinefoodapplication.controller.RestaurantController;
import com.example.onlinefoodapplication.entities.Address;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.Login;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.exceptionhandler.ItemCannotBeDeleted;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresentInRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInArea;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NotLoggedIn;
import com.example.onlinefoodapplication.exceptionhandler.OperationNotAllowed;
import com.example.onlinefoodapplication.models.RestaurantItemsUpdate;
import com.example.onlinefoodapplication.service.LoginService;
import com.example.onlinefoodapplication.service.RestaurantService;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {
	
	@InjectMocks
	RestaurantController controller;
	
	@Mock
	private RestaurantService service;
	
	@Mock
	private LoginService loginService;
	
	MockHttpServletRequest request;

	Restaurant rest;
	
	Login login;
	
	List<Restaurant> restaurants;
	
	List<Item> items;
	@BeforeEach
	void setUp() throws Exception {
		request = new MockHttpServletRequest();
		
		login=new Login();
		login.setLoggedIn(true);
		login.setOwner(true);
		login.setPassword("1234");
		login.setUserid(1);
		login.setUserName("nishad");
		
		
		
		rest=new Restaurant();
		rest.setManagerName("nishad123");
		rest.setRest_id(1);
		rest.setContactNumber("123");
		rest.setName("leela");
		items=new ArrayList<Item>();
		items.add(new Item(34, "mango",90));	
		rest.setItems(items);
		rest.setAddress(new Address(1, "wadegati", 12, "123", "bhandup", "mumbai", "maharashtra", "india", "40042"));
		restaurants= new ArrayList<Restaurant>();
		restaurants.add(rest);
		
		HttpSession session=request.getSession(true);
		session.setAttribute("userDetails", login);
	}

	@Test
	@DisplayName("Testing Add Restaurant")
	void testAddRestaurant() throws NotLoggedIn, OperationNotAllowed {
		
		
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.addRestaurant(rest)).thenReturn(rest);
		
		assertEquals(new ResponseEntity<Restaurant>(service.addRestaurant(rest), HttpStatus.OK), controller.addRestaurant(rest, request));
		
	}
	@Test
	@DisplayName("Testing Add Restaurant 2")
	void testAddRestaurant2() throws NotLoggedIn, OperationNotAllowed {
		when(loginService.checkSession(request)).thenReturn(false);	
		assertThrows(NotLoggedIn.class ,()-> controller.addRestaurant(rest, request));
		
	}
	
	@Test
	@DisplayName("Testing If user is not a owner")
	void testAddRestaurant3() throws NotLoggedIn, OperationNotAllowed {
		
		Login login=new Login();
		login.setLoggedIn(true);
		login.setOwner(false);
		login.setPassword("1234");
		login.setUserid(1);
		login.setUserName("nishad");
		
		HttpSession session=request.getSession(true);
		session.setAttribute("userDetails", login);
		
		when(loginService.checkSession(request)).thenReturn(true);	
		assertThrows(OperationNotAllowed.class ,()-> controller.addRestaurant(rest, request));
		
	}
	@Test
	@DisplayName("Get restaurants")
	void testGetRestaurants() throws NotLoggedIn, OperationNotAllowed {
	
		
		when(loginService.checkSession(request)).thenReturn(true);	
		
		when(service.getRestaurants()).thenReturn(restaurants);
		assertEquals(new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK) ,controller.getRestaurants(request));
		
		
		
	}
	
	@Test
	@DisplayName("Get restaurant by ID")
	void testGetRestaurant() throws NoSuchRestaurant, NotLoggedIn {
		
		
		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.getRestaurant(1)).thenReturn(rest);
		
		assertEquals(new ResponseEntity<Restaurant>(rest, HttpStatus.OK), controller.getRestaurant(1, request));
		
//	)
	}
	
	@Test
	@DisplayName("Get restaurant Items")
	void testGetItemsOfRestaurant() throws NoSuchRestaurant, NotLoggedIn {
		
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getItemsOfARestaurnat(1)).thenReturn(items);
		
		assertEquals(new ResponseEntity<List<Item>>(items, HttpStatus.OK),controller.getItemsOfRestaurant(1, request));
	}
	
	
	@Test
	@DisplayName("Get restaurants by city")
	void GetRestaurantsByCity() throws NoSuchRestaurant, NotLoggedIn, NoRestaurantPresentInCity {
		
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getListOfRestaurantsByCity("mumbai")).thenReturn(restaurants);
		
		assertEquals(new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK),controller.getRestaurantsByCity("mumbai", request));
	}
	@Test
	@DisplayName("Get restaurants by area")
	void GetRestaurantsByArea() throws NoSuchRestaurant, NotLoggedIn,  NoRestaurantPresentInArea {
		
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getListOfRestaurantsByArea("bhandup")).thenReturn(restaurants);
		
		assertEquals(new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK),controller.getRestaurantsByArea("bhandup", request));
	}
	
	@Test
	@DisplayName("Testing Add Item to the restaurant")
	void testAddItemsToRestaurant() throws NotLoggedIn, OperationNotAllowed, NoSuchRestaurant {
		
		RestaurantItemsUpdate update=new RestaurantItemsUpdate();
		update.setRest_id(1);
		List<Item> itemList=new ArrayList<Item>();
		itemList.add(new Item(80,"vada pav",80));
		update.setList(itemList);
		
		
		Restaurant rest=new Restaurant();
		rest.setManagerName("nishad123");
		rest.setRest_id(1);
		rest.setContactNumber("123");
		rest.setName("leela");
		items=new ArrayList<Item>();
		items.add(new Item(34, "mango",90));	
		items.add(new Item(80, "vada pav",80));	
		rest.setItems(items);
		rest.setAddress(new Address(1, "wadegati", 12, "123", "bhandup", "mumbai", "maharashtra", "india", "40042"));
		restaurants= new ArrayList<Restaurant>();
		restaurants.add(rest);
		
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.addItemsToRestaurant(update)).thenReturn(rest);
		
		assertEquals(new ResponseEntity<Restaurant>(rest, HttpStatus.OK), controller.addItemsToRestaurant(update, request));
		
	}
	@Test
	@DisplayName("Delete item from a restaurant")
	void testDeleteItemFromARestaurant() throws NoSuchRestaurant, ItemNotPresentInRestaurant, ItemCannotBeDeleted, NotLoggedIn, OperationNotAllowed {
		Restaurant rest=new Restaurant();
		rest.setManagerName("nishad123");
		rest.setRest_id(1);
		rest.setContactNumber("123");
		rest.setName("leela");
		items=new ArrayList<Item>();
		items.add(new Item(34, "mango",90));	
		items.add(new Item(80, "vada pav",80));	
		rest.setItems(items);
		rest.setAddress(new Address(1, "wadegati", 12, "123", "bhandup", "mumbai", "maharashtra", "india", "40042"));
		restaurants= new ArrayList<Restaurant>();
		restaurants.add(rest);
		
		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.removeItemsFromRestaurant(1, "vada pav")).thenReturn(rest);
		
		assertEquals(new ResponseEntity<String>("Deleted item from the restaurant",HttpStatus.OK), controller.deleteItemFromARestaurant(1, "vada pav", request));
	}
	
	@Test
	@DisplayName("Delete restauarnt")
	void testDelRestaurant() throws NoSuchRestaurant, NotLoggedIn, OperationNotAllowed {
		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.removeRestaurant(1)).thenReturn(rest);
		assertEquals(new ResponseEntity<String>("Found and deleted", HttpStatus.OK), controller.delRestaurant(1, request));
	
	
	}
	

}

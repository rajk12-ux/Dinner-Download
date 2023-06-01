package com.example.Restaurant.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.example.onlinefoodapplication.controller.CustomerController;
import com.example.onlinefoodapplication.entities.Address;
import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.FoodCart;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerAlreadyPresent;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInArea;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NotLoggedIn;
import com.example.onlinefoodapplication.models.CartAdding;
import com.example.onlinefoodapplication.service.CustomerService;
import com.example.onlinefoodapplication.service.LoginService;

@ExtendWith(MockitoExtension.class)

class CustomerControllerTest {
	@InjectMocks
	CustomerController controller;

	@Mock
	private CustomerService service;

	@Mock
	private LoginService loginService;

	Customer cust;

	MockHttpServletRequest request;

	@BeforeEach
	void setUp() {
		request = new MockHttpServletRequest();

		// Customer
		cust = new Customer();
		cust.setCustomerId(1);
		cust.setFirstName("nishad");
		cust.setLastName("kadam");
		cust.setAddress(new Address(1,"building1", 102,"11","bhandup", "mumbai","maharashtra","india","1234"));
		
		
	}

	@Test
	@DisplayName("Adding Customer")
	public void testAddCustomer() throws CustomerAlreadyPresent, NotLoggedIn {
		

		when(loginService.checkSession(request)).thenReturn(true);
		when(service.addCustomer(ArgumentMatchers.any(Customer.class))).thenReturn(cust);

		assertEquals(new ResponseEntity<Customer>(cust, HttpStatus.OK), controller.addCustomer(cust, request));

	}

	@Test
	@DisplayName("Exception Testing in employee")
	public void testAddEmployee2() throws CustomerAlreadyPresent, NotLoggedIn {
		MockHttpServletRequest request = new MockHttpServletRequest();
		when(loginService.checkSession(request)).thenReturn(false);
		assertThrows(NotLoggedIn.class, () -> controller.addCustomer(cust, request));

	}

	@Test
	@DisplayName("Exception Testing in employee")
	public void testAddEmployee3() throws CustomerAlreadyPresent, NotLoggedIn {

		MockHttpServletRequest request = new MockHttpServletRequest();

		when(loginService.checkSession(request)).thenReturn(false);

		assertThrows(NotLoggedIn.class, () -> controller.addCustomer(cust, request));

	}

	@Test
	@DisplayName("Get Customers")
	public void testGetCustomers() throws NotLoggedIn {

		List<Customer> list = new ArrayList<Customer>();
		list.add(cust);
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getAllCustomers()).thenReturn(list);

		assertEquals(new ResponseEntity<List<Customer>>(list, HttpStatus.OK), controller.getCustomers(request));

	}

	@Test
	@DisplayName("Get Customer by Id")
	public void testGetCustomerById() throws CustomerNotPresent, NotLoggedIn {
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getCustomerById(1)).thenReturn(cust);
		assertEquals(new ResponseEntity<Customer>(cust, HttpStatus.OK), controller.getCustomerById(1, request));
	}
	
	@Test
	@DisplayName("Get Customer by Id Exception Test")
	public void testGetCustomerById2() throws CustomerNotPresent, NotLoggedIn {
		Customer c=null;
		
		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getCustomerById(1)).thenReturn(null);
		
		assertEquals(new ResponseEntity<Customer>(c, HttpStatus.INTERNAL_SERVER_ERROR), controller.getCustomerById(1, request));
	}
	
	@Test
	@DisplayName("Get Customer by city name")
	public void testGetCustomerByCity() throws CustomerNotPresent, NotLoggedIn, NoCustomerPresentInCity {
		
		
		when(loginService.checkSession(request)).thenReturn(true);
		
		List<Customer> list=new ArrayList<Customer>();
		list.add(cust);
		
		when(service.getCustomerByCity("mumbai")).thenReturn(list);
		
		assertEquals(new ResponseEntity<List<Customer>>(list, HttpStatus.OK), controller.getCustomerByCity("mumbai", request));
	
	}
	
	@Test
	@DisplayName("Get Customer by city name")
	public void testGetCustomerByCity2() throws CustomerNotPresent, NotLoggedIn, NoCustomerPresentInCity {
		
		List<Customer> cust=null;
		
		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.getCustomerByCity("mumbai")).thenReturn(null);
		
		assertEquals(new ResponseEntity<List<Customer>>(cust, HttpStatus.NO_CONTENT), controller.getCustomerByCity("mumbai", request));
	
	}
	
	@Test
	@DisplayName("Get Customer by area name")
	public void testGetCustomerByArea() throws CustomerNotPresent, NotLoggedIn,  NoCustomerPresentInArea {
		
		List<Customer> cust=null;
		
		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.getCustomerByArea("bhandup")).thenReturn(null);
		
		assertEquals(new ResponseEntity<List<Customer>>(cust, HttpStatus.NO_CONTENT), controller.getCustomerByArea("bhandup", request));
	
	}
	
	
	@Test
	@DisplayName("Get Customer by area name")
	public void testGetCustomerByArea2() throws CustomerNotPresent, NotLoggedIn,  NoCustomerPresentInArea {
		
		when(loginService.checkSession(request)).thenReturn(true);
		
		List<Customer> list=new ArrayList<Customer>();
		list.add(cust);
		
		when(service.getCustomerByArea("bhandup")).thenReturn(list);
		
		assertEquals(new ResponseEntity<List<Customer>>(list, HttpStatus.OK), controller.getCustomerByArea("bhandup", request));
	}
	
	
	
	@Test
	@DisplayName("Update Customer")
	public void testUpdateCustomer() throws CustomerAlreadyPresent, NotLoggedIn, CustomerNotPresent {
		MockHttpServletRequest request = new MockHttpServletRequest();

		when(loginService.checkSession(request)).thenReturn(true);
		when(service.updateCustomer(ArgumentMatchers.any(Customer.class))).thenReturn(cust);

		assertEquals(new ResponseEntity<Customer>(cust, HttpStatus.OK), controller.updateCustomer(cust, request));

	}

	@Test
	@DisplayName("Delete Customer")
	public void testDeleteCustomer() throws CustomerAlreadyPresent, NotLoggedIn, CustomerNotPresent {
		

		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.deleteCustomer(1)).thenReturn(cust);

		assertEquals(new ResponseEntity<String>("Customer deleted Successfully", HttpStatus.OK), controller.deleteCustomer(1, request));

	}
	@Test
	@DisplayName("Delete Customer 2")
	public void testDeleteCustomer2() throws CustomerAlreadyPresent, NotLoggedIn, CustomerNotPresent {
		

		when(loginService.checkSession(request)).thenReturn(true);
		
		when(service.deleteCustomer(1)).thenReturn(null);

		assertEquals(new ResponseEntity<String>("item was not deleted", HttpStatus.BAD_REQUEST), controller.deleteCustomer(1, request));

	}
	
	@Test
	@DisplayName("Adding item to Customer cart")
	public void testAddItemToCart() throws CustomerAlreadyPresent, NotLoggedIn, ItemNotPresent, CustomerNotPresent {
		

		when(loginService.checkSession(request)).thenReturn(true);
		when(service.addItemToACart(ArgumentMatchers.any(CartAdding.class))).thenReturn(cust);

		List<Item> items=new ArrayList<Item>();
		items.add(new Item(34, "mango",90));		
		assertEquals(new ResponseEntity<Customer>(cust, HttpStatus.OK), controller.addItemsToTheCart(new CartAdding(1,items), request));

	}
	
	@Test
	@DisplayName("Deleteing item from Customer cart")
	public void testDeleteItemFromCart() throws CustomerAlreadyPresent, NotLoggedIn, ItemNotPresent, CustomerNotPresent, CartIsEmpty {
		

		when(loginService.checkSession(request)).thenReturn(true);
		when(service.deleteItemFromCart(1, 20)).thenReturn(cust);
		
		assertEquals(new ResponseEntity<Customer>(cust, HttpStatus.OK), controller.deleteItemFromCart(1, 20, request));

	}
	
	
	@Test
	@DisplayName("Viewing cart")
	public void testViewCart() throws CustomerAlreadyPresent, NotLoggedIn, ItemNotPresent, CustomerNotPresent, CartIsEmpty {
		FoodCart cart=new FoodCart();
		cart.setCart_id(1);
		List<Item> items=new ArrayList<Item>();
		items.add(new Item(34, "mango",90));
		
		cart.setItems(items);

		when(loginService.checkSession(request)).thenReturn(true);
		when(service.getCart(1)).thenReturn(cart);
		
		assertEquals(new ResponseEntity<FoodCart>(cart, HttpStatus.OK), controller.getCart(1, request));

	}

}

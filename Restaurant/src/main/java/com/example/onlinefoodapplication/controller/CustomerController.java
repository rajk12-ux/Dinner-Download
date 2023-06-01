package com.example.onlinefoodapplication.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.FoodCart;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerAlreadyPresent;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInArea;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NotLoggedIn;
import com.example.onlinefoodapplication.models.CartAdding;
import com.example.onlinefoodapplication.service.CustomerService;
import com.example.onlinefoodapplication.service.LoginService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService service;

	@Autowired
	private LoginService loginService;

	@PostMapping("/addCustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer cust, HttpServletRequest request)
			throws CustomerAlreadyPresent, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		Customer customer = service.addCustomer(cust);
		if (customer == null) {
			return new ResponseEntity<Customer>(customer, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@GetMapping("/getCustomers")
	public ResponseEntity<List<Customer>> getCustomers(HttpServletRequest request) throws NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		return new ResponseEntity<List<Customer>>(service.getAllCustomers(), HttpStatus.OK);
	}

	@GetMapping("/getCustomerById/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id, HttpServletRequest request)
			throws CustomerNotPresent, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		Customer temp = service.getCustomerById(id);
		if (temp == null) {
			return new ResponseEntity<Customer>(temp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Customer>(temp, HttpStatus.OK);
	}

	@GetMapping("/getCustomerByCity/{city_name}")
	public ResponseEntity<List<Customer>> getCustomerByCity(@PathVariable("city_name") String city_name,
			HttpServletRequest request) throws NoCustomerPresentInCity, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		List<Customer> temp = service.getCustomerByCity(city_name);
		if (temp == null) {
			return new ResponseEntity<List<Customer>>(temp, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(temp, HttpStatus.OK);
	}

	@GetMapping("/getCustomerByArea/{area_name}")
	public ResponseEntity<List<Customer>> getCustomerByArea(@PathVariable("area_name") String area_name,
			HttpServletRequest request) throws NoCustomerPresentInArea, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		List<Customer> temp = service.getCustomerByArea(area_name);
		if (temp == null) {
			return new ResponseEntity<List<Customer>>(temp, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(temp, HttpStatus.OK);
	}

	@PutMapping("/updateCustomer")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer cust, HttpServletRequest request)
			throws CustomerNotPresent, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		Customer customer = service.updateCustomer(cust);
		if (customer == null) {
			return new ResponseEntity<Customer>(customer, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id, HttpServletRequest request)
			throws CustomerNotPresent, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		Customer cust = service.deleteCustomer(id);
		if (cust == null) {
			return new ResponseEntity<String>("item was not deleted", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Customer deleted Successfully", HttpStatus.OK);
	}

	@PutMapping("/addItemsToTheCart")
	public ResponseEntity<Customer> addItemsToTheCart(@RequestBody CartAdding add, HttpServletRequest request)
			throws ItemNotPresent, CustomerNotPresent, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		Customer c = service.addItemToACart(add);
		if (c == null) {
			return new ResponseEntity<Customer>(c, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>(c, HttpStatus.OK);
	}

	@DeleteMapping("/deleteItemsFromCart/{cust_id}/{item_id}")
	public ResponseEntity<Customer> deleteItemFromCart(@PathVariable("cust_id") int cust,
			@PathVariable("item_id") int item, HttpServletRequest request)
			throws ItemNotPresent, CustomerNotPresent, CartIsEmpty, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		Customer c = service.deleteItemFromCart(cust, item);
		if (c == null) {
			return new ResponseEntity<Customer>(c, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>(c, HttpStatus.OK);
	}

	@GetMapping("/viewCart/{id}")
	public ResponseEntity<FoodCart> getCart(@PathVariable("id") int id, HttpServletRequest request)
			throws CustomerNotPresent, NotLoggedIn {

		// checking login
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}

		FoodCart cart = service.getCart(id);
		if (cart == null) {
			return new ResponseEntity<FoodCart>(cart, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FoodCart>(cart, HttpStatus.OK);
	}

}

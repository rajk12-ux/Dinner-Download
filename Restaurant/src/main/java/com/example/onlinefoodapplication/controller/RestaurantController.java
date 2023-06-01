package com.example.onlinefoodapplication.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.Login;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.exceptionhandler.ItemCannotBeDeleted;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresentInRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NoItemsInRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInArea;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NotLoggedIn;
import com.example.onlinefoodapplication.exceptionhandler.OperationNotAllowed;
import com.example.onlinefoodapplication.models.RestaurantDetailsUpdate;
import com.example.onlinefoodapplication.models.RestaurantItemsUpdate;
import com.example.onlinefoodapplication.service.LoginService;
import com.example.onlinefoodapplication.service.RestaurantService;

@RestController
public class RestaurantController {

	@Autowired
	private RestaurantService service;
	
	@Autowired
	private LoginService loginService;

	@PostMapping("/restaurant")
	public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant res,HttpServletRequest request) throws NotLoggedIn, OperationNotAllowed {
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		// owner check
		Login currentUser = (Login) request.getSession().getAttribute("userDetails");
		if (!currentUser.isOwner()) {
			throw new OperationNotAllowed("Operation not allowed");
		}
		
		
		return new ResponseEntity<Restaurant>(service.addRestaurant(res), HttpStatus.OK);
	}
	@PostMapping("/restaurantDetailsUpdate")
	public ResponseEntity<Restaurant> updateRestaurantDetails
	(@RequestBody RestaurantDetailsUpdate update , HttpServletRequest request) throws NoSuchRestaurant, NotLoggedIn, OperationNotAllowed
	{
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		// owner check
		com.example.onlinefoodapplication.entities.Login currentUser = (Login) request.getSession().getAttribute("userDetails");
		if (!currentUser.isOwner()) {
			throw new OperationNotAllowed("Operation not allowed");
		}
		
		
		Restaurant temp=service.updateRestaurantDetails(update);
		if(temp==null) {
			return new ResponseEntity<Restaurant>(temp,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<Restaurant>(temp,HttpStatus.OK);
	}
	

	@DeleteMapping("/delrestaurant/{id}")
	public ResponseEntity<String> delRestaurant(@PathVariable("id") int id,  HttpServletRequest request) throws NoSuchRestaurant, NotLoggedIn, OperationNotAllowed {
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		// owner check
		com.example.onlinefoodapplication.entities.Login currentUser = (Login) request.getSession().getAttribute("userDetails");
		if (!currentUser.isOwner()) {
			throw new OperationNotAllowed("Operation not allowed");
		}
		

		Restaurant res = service.removeRestaurant(id);
		if (res == null) {
			return new ResponseEntity<String>("Some Error Occured", HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<String>("Found and deleted", HttpStatus.OK);
	}

	@GetMapping("/restaurants")
	public ResponseEntity<List<Restaurant>> getRestaurants(HttpServletRequest request) throws NotLoggedIn {
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		
		List<Restaurant> rests = service.getRestaurants();
		if (rests == null) {
			return new ResponseEntity<List<Restaurant>>(new ArrayList<Restaurant>(), HttpStatus.OK);
		}

		return new ResponseEntity<List<Restaurant>>(rests, HttpStatus.OK);

	}
	
	@GetMapping("/restaurantById/{id}")
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") int id, HttpServletRequest request) throws NoSuchRestaurant, NotLoggedIn{
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		Restaurant rest=service.getRestaurant(id);
		return new ResponseEntity<Restaurant>(rest, HttpStatus.OK);
		
	}

	@GetMapping("/restaurnt-items/{rest_id}")
	public ResponseEntity<List<Item>> getItemsOfRestaurant(@PathVariable("rest_id") int res, HttpServletRequest request) throws NoSuchRestaurant, NotLoggedIn {
		
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		List<Item> rests = service.getItemsOfARestaurnat(res);
		if (rests == null) {
			return new ResponseEntity<List<Item>>(new ArrayList<Item>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Item>>(rests, HttpStatus.OK);

	}

	@GetMapping("/restaurantsByCity/{city}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByCity(@PathVariable("city") String city, HttpServletRequest request) throws NoRestaurantPresentInCity, NotLoggedIn {
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		List<Restaurant> rests = service.getListOfRestaurantsByCity(city);
		if (rests == null) {
			return new ResponseEntity<List<Restaurant>>(new ArrayList<Restaurant>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Restaurant>>(rests, HttpStatus.OK);

	}

	@GetMapping("/restaurantsByArea/{area}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByArea(@PathVariable("area") String area, HttpServletRequest request) throws NoRestaurantPresentInArea, NotLoggedIn {
		
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		List<Restaurant> rests = service.getListOfRestaurantsByArea(area);
		if (rests == null) {
			return new ResponseEntity<List<Restaurant>>(new ArrayList<Restaurant>(), HttpStatus.OK);
		}

		return new ResponseEntity<List<Restaurant>>(rests, HttpStatus.OK);

	}

	@PostMapping("/addItemsToRestaurant")
	public ResponseEntity<Restaurant> addItemsToRestaurant(@RequestBody RestaurantItemsUpdate update, HttpServletRequest request) throws NoSuchRestaurant, NotLoggedIn, OperationNotAllowed {
		
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		// owner check
		com.example.onlinefoodapplication.entities.Login currentUser = (Login) request.getSession().getAttribute("userDetails");
		if (!currentUser.isOwner()) {
			throw new OperationNotAllowed("Operation not allowed");
		}
		
		
		
		Restaurant rest = service.addItemsToRestaurant(update);
		if(rest==null) {
			
			return new ResponseEntity<Restaurant>(rest, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Restaurant>(rest, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteItemFromRestaurant/{rest_id}/{item_name}")
	public ResponseEntity<String> deleteItemFromARestaurant(
			@PathVariable("rest_id") int id,
			@PathVariable("item_name") String name,
			HttpServletRequest request
			) throws NoSuchRestaurant, ItemNotPresentInRestaurant, ItemCannotBeDeleted, NotLoggedIn, OperationNotAllowed
	{
		
		
		boolean validLogin = loginService.checkSession(request);
		if (!validLogin) {
			throw new NotLoggedIn("Not logged in");
		}
		
		// owner check
		com.example.onlinefoodapplication.entities.Login currentUser = (Login) request.getSession().getAttribute("userDetails");
		if (!currentUser.isOwner()) {
			throw new OperationNotAllowed("Operation not allowed");
		}
		
		Restaurant updated=service.removeItemsFromRestaurant(id, name);
		if(updated==null) {
			return new ResponseEntity<String>("Item or RestaurantID not found",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Deleted item from the restaurant",HttpStatus.OK);
		
	}
	
	

}

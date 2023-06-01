package com.example.onlinefoodapplication.service;

import java.util.List;

import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.exceptionhandler.ItemCannotBeDeleted;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresentInRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NoItemsInRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInArea;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchRestaurant;
import com.example.onlinefoodapplication.models.RestaurantDetailsUpdate;
import com.example.onlinefoodapplication.models.RestaurantItemsUpdate;

public interface RestaurantService {
	Restaurant addRestaurant(Restaurant res);
	
	Restaurant removeRestaurant(int id) throws NoSuchRestaurant;
	
	List<Restaurant> getRestaurants();

	List<Item> getItemsOfARestaurnat(int res_id) throws  NoSuchRestaurant;

	List<Restaurant> getListOfRestaurantsByCity(String city) throws NoRestaurantPresentInCity;

	List<Restaurant> getListOfRestaurantsByArea(String area) throws NoRestaurantPresentInArea;

	Restaurant addItemsToRestaurant(RestaurantItemsUpdate update) throws NoSuchRestaurant;
	
	Restaurant removeItemsFromRestaurant(int id, String name) throws NoSuchRestaurant, ItemNotPresentInRestaurant, ItemCannotBeDeleted;

	Restaurant updateRestaurantDetails(RestaurantDetailsUpdate update) throws NoSuchRestaurant;

	Restaurant getRestaurant(int id) throws NoSuchRestaurant;
	
	
	
}

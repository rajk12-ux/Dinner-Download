package com.example.onlinefoodapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.example.onlinefoodapplication.repo.ItemRepository;
import com.example.onlinefoodapplication.repo.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepository repo;
	
	@Autowired
	private ItemRepository item_repo;
	
	@Override
	public Restaurant addRestaurant(Restaurant res) {
		return repo.save(res);
	}

	@Override
	public Restaurant removeRestaurant(int id) throws NoSuchRestaurant {
		Restaurant temp=repo.findById(id).orElse(null);

		if(temp==null) {
			throw new NoSuchRestaurant("There is no such restaurant");
		}
		repo.delete(temp);
		return temp;
	}

	@Override
	public List<Restaurant> getRestaurants() {
		return repo.findAll();
	}

	@Override
	public List<Item> getItemsOfARestaurnat(int res_id) throws NoSuchRestaurant {
		List<Item> list= repo.getItemsOfARestaurant(res_id);
		if(list.size()==0) {
			throw new NoSuchRestaurant("No such Restaurant");
		}
		return list;
	}

	@Override
	public List<Restaurant> getListOfRestaurantsByCity(String city) throws NoRestaurantPresentInCity {
		List<Restaurant> list=repo.getRestaurantsByCity(city);
		if(list.size()==0) {
			throw new NoRestaurantPresentInCity("No restaurant present");
		}
		return list;
	}

	@Override
	public List<Restaurant> getListOfRestaurantsByArea(String area) throws NoRestaurantPresentInArea {
		List<Restaurant> list= repo.getRestaurantsByArea(area);
		if(list.size()==0) {
			throw new NoRestaurantPresentInArea("No restaurant present");
		}
		return list;
	}

	@Override
	public Restaurant addItemsToRestaurant(RestaurantItemsUpdate update) throws NoSuchRestaurant {
		Restaurant temp=repo.findById(update.getRest_id()).orElse(null);
		if(temp==null) {
			throw new NoSuchRestaurant("No such restaurant");
		} 
		
		temp.getItems().addAll(update.getList());
		repo.save(temp);
		return temp;
	}

	@Override
	public Restaurant removeItemsFromRestaurant(int id, String name) throws NoSuchRestaurant, ItemNotPresentInRestaurant, ItemCannotBeDeleted {
		Restaurant temp=repo.findById(id).orElse(null);
		if(temp==null) {
			throw new NoSuchRestaurant("No such restaurant");
		} 
		
		//ManytoMany
		List<Item> newList=
		temp.getItems().stream().filter(e->!e.getName().equalsIgnoreCase(name))
			.collect(Collectors.toList());
		
		if(newList.size()==temp.getItems().size()) {
			throw new ItemNotPresentInRestaurant("Item not present in restaurant");
		}
		Item item=temp.getItems().stream()
				.filter(e->e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
		
		System.out.println(item);
		if(item==null) {
			return null;
		}
		
		if(newList.size()==0) {
			throw new ItemCannotBeDeleted("Item cannot be deleted");
		}
		
		
		item_repo.deleteById(item.getItem_id());
		System.out.println(newList);
		temp.setItems(newList);
		Restaurant newSaved=repo.save(temp);
		return newSaved;
	}

	@Override
	public Restaurant updateRestaurantDetails(RestaurantDetailsUpdate update) throws NoSuchRestaurant {
		Restaurant temp=repo.findById(update.getId()).orElse(null);
		if(temp==null) {
			throw new NoSuchRestaurant("there is no such restaurant");
		}
		temp.setName(update.getName());
		temp.setContactNumber(update.getContact());
		temp.setManagerName(update.getManagerName());
		
		Restaurant temp2 =repo.save(temp);
		return temp2;
	}

	@Override
	public Restaurant getRestaurant(int id) throws NoSuchRestaurant {
		Restaurant rest= repo.findById(id).orElse(null);
		if(rest==null) {
			throw new NoSuchRestaurant("There is no such Restaurant");
		}
		return rest;
	}

}

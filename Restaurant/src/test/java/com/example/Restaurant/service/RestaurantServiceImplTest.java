package com.example.Restaurant.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.onlinefoodapplication.entities.Address;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.Rating;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.entities.Review;
import com.example.onlinefoodapplication.exceptionhandler.ItemCannotBeDeleted;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresentInRestaurant;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchRestaurant;
import com.example.onlinefoodapplication.models.RestaurantDetailsUpdate;
import com.example.onlinefoodapplication.models.RestaurantItemsUpdate;
import com.example.onlinefoodapplication.repo.ItemRepository;
import com.example.onlinefoodapplication.repo.RestaurantRepository;
import com.example.onlinefoodapplication.service.RestaurantServiceImpl;



class RestaurantServiceImplTest {
	
	@Mock
	RestaurantRepository repo;
	
	@Mock
	ItemRepository itemrepo;
	
	@InjectMocks
	RestaurantServiceImpl service;
	
	Address address1;
	Address address2;


	Restaurant restaurant1;
	Restaurant restaurant2;

	
	RestaurantDetailsUpdate restdetailsupdate;
	RestaurantItemsUpdate restitemupdate;
	Rating rating1;
	List<Item> itemList1 = new ArrayList<>();
	List<Item> itemList2 = new ArrayList<>();
	List<Review> reviews = new ArrayList<>();
	List<Item> itemList3 = new ArrayList<>();
	
	@BeforeEach
	public void initialize() {
		MockitoAnnotations.openMocks(this);
		
		this.itemList1.add(new Item(1, "Idly", 45));
		this.itemList2.add(new Item(2,"Dosa",50));
		this.reviews.add(new Review(21,"Good food and service"));
		address1 = new Address(1, "building1",201, "street1", "area1", "city1", "state1", "country1", "pincode1");
		address2 = new Address(2, "building2",202, "street2", "area2", "city2", "state2", "country2", "pincode2");

		rating1=new Rating(1,90,90);

		restaurant1 = new Restaurant(21, "reasturant1", address1, this.rating1,this.itemList1,this.reviews, "manager1", "contact1");
		restaurant2 = new Restaurant(22, "reasturant2", address2, this.rating1,this.itemList2,this.reviews, "manager2", "contact2");
		

		restitemupdate=new RestaurantItemsUpdate(21, itemList2);
		
		restdetailsupdate=new RestaurantDetailsUpdate(21,"A2B","Rock","6565");
	}
	
	@Test
	@DisplayName("Add Restaurant")
	void addRestaurantTest() throws Exception {
		Mockito.when(repo.save(restaurant1)).thenReturn(restaurant1);
		assertThat(service.addRestaurant(restaurant1)).isEqualTo(restaurant1);
	}
	
	@Test
	@DisplayName("Remove Restaurant")
	void removeRestaurantTest() throws Exception {
		ArgumentCaptor<Restaurant> valueCapture = ArgumentCaptor.forClass(Restaurant.class);
		doNothing().when(repo).delete(valueCapture.capture());
		Mockito.when(repo.findById(restaurant1.getRest_id())).thenReturn(Optional.of(restaurant1));
		assertThat(service.removeRestaurant(restaurant1.getRest_id())).isEqualTo(restaurant1);
		
	}
	@Test
	@DisplayName("Update Restaurant Details")
	void updateRestaurantTest() throws Exception {
		
		Restaurant restaurant3=
				new Restaurant(21, "reasturant1", new Address(1, "building1",201, "street1", "area1", "city1", "state1", "country1", "pincode1")
						, this.rating1,
						this.itemList1,
						this.reviews,
						"manager1",
						"contact1");
		restaurant3.setName("Yoo");
		restaurant3.setManagerName("nishad");
		restaurant3.setContactNumber("66");;
		
		Mockito.when(repo.findById(restdetailsupdate.getId())).thenReturn(Optional.of(restaurant1));
		Mockito.when(repo.save(ArgumentMatchers.any(Restaurant.class))).thenReturn(restaurant3);
		
		assertThat(service.updateRestaurantDetails(restdetailsupdate)).isEqualTo(restaurant3);
	}
	
	@Test
	@DisplayName(" View all Restaurant")
	void viewAllRestaurantTest() throws Exception {
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant1);
		restaurants.add(restaurant2);
		Mockito.when(repo.findAll()).thenReturn(restaurants);
		assertThat(service.getRestaurants()).isEqualTo(restaurants);
	}
	
	@Test 
	@DisplayName("View Items of Resturant")
	void getItemsOfARestaurantTest() throws Exception {
		int id = 21;
		List<Item> items=restaurant1.getItems();
		Mockito.when(repo.getItemsOfARestaurant(id)).thenReturn(restaurant1.getItems());
		assertThat(service.getItemsOfARestaurnat(id)).isEqualTo(items);
		
	}
	@Test
	@DisplayName("Restaurants by City")
	void getListOfRestaurantsByCityTest() throws Exception {
		String name = "city1";
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant1);
		Mockito.when(repo.getRestaurantsByCity(name)).thenReturn(restaurants);
		assertThat(service.getListOfRestaurantsByCity(name)).isEqualTo(restaurants);
	}
	
	@Test
	@DisplayName("Restaurants by Area")
	void getListOfRestaurantsByAreaTest() throws Exception {
		String name = "area1";
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant1);
		
		
		Mockito.when(repo.getRestaurantsByArea(name)).thenReturn(restaurants);
	

		assertThat(service.getListOfRestaurantsByArea(name)).isEqualTo(restaurants);
	}
	@Test
	@DisplayName("Restaurant by Id")
	void getRestaurantbyIdTest() throws Exception{
		
		Mockito.when(repo.findById(21)).thenReturn(Optional.of(restaurant1));
		
		assertThat(service.getRestaurant(21)).isEqualTo(restaurant1);
	}
	
	
	@Test
	@DisplayName("Restaurant Item update")
	void addItemsToRestaurantTest() throws Exception {
		Restaurant restaurant3=
				new Restaurant(21, "reasturant1", new Address(1, "building1",201, "street1", "area1", "city1", "state1", "country1", "pincode1")
						, this.rating1,
						new ArrayList<Item>(this.itemList1),
						this.reviews,
						"manager1",
						"contact1");
		System.out.println(restaurant3);
		System.out.println(restaurant1);

		RestaurantItemsUpdate update = new RestaurantItemsUpdate();
		update.setRest_id(21);
		List<Item> itemList=new ArrayList<Item>();
		itemList.add(new Item(34,"popcorn",80));
		update.setList(itemList);

		restaurant3.getItems().add(new Item(34,"popcorn",80));
		
		
		Mockito.when(repo.findById(restitemupdate.getRest_id())).thenReturn(Optional.of(restaurant1));
		
		when(repo.save(ArgumentMatchers.any(Restaurant.class))).thenReturn(restaurant3);
		assertThat(service.addItemsToRestaurant(update)).isEqualTo(restaurant3);
		
	}
	
	@Test
	@DisplayName("Remove Item from restaurant")
	void removeItemsFromRestaurantTest() throws NoSuchRestaurant, ItemNotPresentInRestaurant, ItemCannotBeDeleted  {
		Restaurant restaurant3=
				new Restaurant(21, "reasturant1", new Address(1, "building1",201, "street1", "area1", "city1", "state1", "country1", "pincode1")
						, this.rating1,
						new ArrayList<Item>(this.itemList1),
						this.reviews,
						"manager1",
						"contact1");
		restaurant3.getItems().add(new Item(60,"popcorn",89));
		System.out.println(restaurant3);
		
		Restaurant restaurant4=
				new Restaurant(21, "reasturant1", new Address(1, "building1",201, "street1", "area1", "city1", "state1", "country1", "pincode1")
						, this.rating1,
						new ArrayList<Item>(this.itemList1),
						this.reviews,
						"manager1",
						"contact1");
		
		Mockito.when(repo.findById(21)).thenReturn(Optional.of(restaurant3));
		
		when(repo.save(ArgumentMatchers.any(Restaurant.class))).thenReturn(restaurant4);
		assertThat(service.removeItemsFromRestaurant(21, "popcorn")).isEqualTo(restaurant4);
		
	}
	
	
}

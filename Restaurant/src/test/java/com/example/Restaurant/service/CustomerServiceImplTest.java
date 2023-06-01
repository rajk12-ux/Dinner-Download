package com.example.Restaurant.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.FoodCart;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.Rating;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.entities.Review;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerAlreadyPresent;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInCity;
import com.example.onlinefoodapplication.models.CartAdding;
import com.example.onlinefoodapplication.repo.CustomerRepository;
import com.example.onlinefoodapplication.repo.ItemRepository;
import com.example.onlinefoodapplication.service.CustomerServiceImpl;


@DisplayName("Customer Service Test Cases")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceImplTest {
	
	@InjectMocks
	private CustomerServiceImpl service;
	
	@Mock
	private CustomerRepository repo;
	
	@Mock
	private ItemRepository item_repo;
	
	Address add1;
	Address add2;

	Customer c1;
	Customer c2;
	
	Review r1;
	List<Review> reviews = new ArrayList<>();
	
	Rating ra1;

	Restaurant rest1;
	List<Restaurant> rest = new ArrayList<>();

	Item item1,item2;
	List<Item> itemList = new ArrayList<>();

	FoodCart cart,cart2;
	
	CartAdding cartAdd;
	
	@BeforeEach
	public void initialize() {
		MockitoAnnotations.openMocks(this);
		
		add1 = new Address(101, "Ketki Complex", 12,"Street 1", "Poonam Nagar", "Mumbai", "Maharashtra", "India", "401303");
		add2 = new Address(102, "Gokul Park", 15,"Street 2", "M.K. Road", "Mumbai", "Maharashtra", "India", "401107");
		
		item1 = new Item(1,"Papdi",50);
		itemList.add(item1);
		cart = new FoodCart(1, itemList);
		
		r1=new Review(1,"Great food!");
		reviews.add(r1);
		
		ra1=new Rating(1,8,9);

		rest1 = new Restaurant(1001, "ResA", add1, ra1, itemList, reviews, "Rahul Rai", "1213512135");
		rest.add(rest1);
		
		//Target
		c1 = new Customer(111, "Riya", "Patel", 22, "Female", "9236142754", "riyap23@domain.com", add1, cart, true);
		
		item2 = new Item(2,"Chaat",60);
		itemList.add(item2);
		cart2 = new FoodCart(2, itemList);
		
		c2 = new Customer(112, "Alina", "Shaikh", 24, "Female", "7856942324", "alinashaikh@domain.com", add2, cart2, true);
		
		cartAdd=new CartAdding(1,itemList);
	}

	@Test
	@Order(9)
	public void testaddItemToCart() throws ItemNotPresent, CustomerNotPresent { 
		Customer dummyc1=new Customer(111, "Riya", "Patel", 22, "Female", "9236142754", 
				"riyap23@domain.com", new Address(101, "Ketki Complex", 12,"Street 1", "Poonam Nagar", "Mumbai", "Maharashtra", "India", "401303"),
				 new FoodCart(1,new ArrayList<Item>(itemList)), true);
		
		CartAdding cartAdd=new CartAdding();
		cartAdd.setId(111);
		List<Item> itemList=new ArrayList<Item>();
		itemList.add(new Item(20,"lolipop",50));
		
		cartAdd.setList(itemList);		
		FoodCart cart=c1.getCart();
		
		for(Item item:cartAdd.getList()) {
			c1.getCart().getItems().add(item);		
		}
		
		
		
		when(repo.findById(111)).thenReturn(Optional.ofNullable(dummyc1));
		when(item_repo.findById(20)).thenReturn(Optional.ofNullable(new Item(20,"lolipop",50)));
		
		when(repo.save(ArgumentMatchers.any(Customer.class))).thenReturn(c1);		
		
		assertEquals(c1, service.addItemToACart(cartAdd));
	
	
	}
	
	@Test
	@Order(10)
	public void testDeleteItemFromCart() throws ItemNotPresent, CustomerNotPresent, CartIsEmpty {
		Customer dummyc1=new Customer(111, "Riya", "Patel", 22, "Female", "9236142754", 
				"riyap23@domain.com", new Address(101, "Ketki Complex", 12,"Street 1", "Poonam Nagar", "Mumbai", "Maharashtra", "India", "401303"),
				 new FoodCart(1,new ArrayList<Item>(itemList)), true);
		System.out.println("c1"+dummyc1);
		
		
		Customer dummyc2= new Customer(111, "Riya", "Patel", 22, "Female", "9236142754", 
				"riyap23@domain.com", new Address(101, "Ketki Complex", 12,"Street 1", "Poonam Nagar", "Mumbai", "Maharashtra", "India", "401303"),
				 new FoodCart(1,new ArrayList<Item>(itemList)), true);
		
		
		dummyc2.getCart().getItems().remove(1);
		System.out.println("c2"+dummyc2);
		when(repo.findById(111)).thenReturn(Optional.ofNullable(dummyc1));
		when(repo.save(ArgumentMatchers.any(Customer.class))).thenReturn(dummyc2);
		assertEquals(dummyc2, service.deleteItemFromCart(111, 2));
		
//		when(repo.findById(111)).thenReturn(Optional.ofNullable(dummyc1));
//		when(repo.save(ArgumentMatchers.any(Customer.class))).thenReturn(dummyc2);
//		
//		assertEquals(dummyc2, service.deleteItemFromCart(111, 2));)
		
		
		
	}
	
	
	
	@Test
	@Order(1)
	public void testAddCustomer() throws Exception {
		when(repo.save(c1)).thenReturn(c1);
		assertEquals(c1, service.addCustomer(c1));
	}
	@Test
	@Order(2)
	void testUpdateCustomer() throws Exception {
		Mockito.when(repo.save(c1)).thenReturn(c1);
		Mockito.when(repo.existsById(c1.getCustomerId())).thenReturn(true);

		Mockito.when(repo.findById(c1.getCustomerId())).thenReturn(Optional.of(c1));
		assertThat(service.updateCustomer(c1)).isEqualTo(c1);
	}
////	
	@Test
	@Order(3)
	void testDeleteCustomer() throws CustomerNotPresent {
		ArgumentCaptor<Customer> valueCapture = ArgumentCaptor.forClass(Customer.class);
		doNothing().when(repo).delete(valueCapture.capture());
		Mockito.when(repo.findById(c1.getCustomerId())).thenReturn(Optional.of(c1));
		assertThat(service.deleteCustomer(c1.getCustomerId())).isEqualTo(c1);
	}
////	
	@Test
	@Order(4)
	void testGetAllCustomers() {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(c1);
		Mockito.when(repo.findAll()).thenReturn(customerList);
		List<Customer> res = service.getAllCustomers();
		assertThat(res.size()).isEqualTo(1);
	}
////	
	@Test
	@Order(5)
	public void testGetCustomerById() throws CustomerNotPresent {
		Mockito.doReturn(Optional.of(c1)).when(repo).findById(c1.getCustomerId());
		assertThat(service.getCustomerById(c1.getCustomerId())).isEqualTo(c1);
	}
////	
	@Test
	@Order(6)
	public void testGetCart() throws CustomerNotPresent {
		Mockito.doReturn(Optional.of(c1)).when(repo).findById(c1.getCustomerId());
		assertThat(service.getCart(c1.getCustomerId())).isEqualTo(c1.getCart());
	}
////	
	@Test
	@Order(7)
	public void testGetCustomerByCity() throws NoCustomerPresentInCity {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(c1);
		Mockito.when(repo.getCustomerByCity(c1.getAddress().getCity())).thenReturn(customerList);
		assertThat(service.getCustomerByCity(add1.getCity())).isEqualTo(customerList);
		
	}
//
	@Test
	@Order(8)
	public void testGetCustomerByArea() throws NoCustomerPresentInCity {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(c1);
		Mockito.when(repo.getCustomerByCity(c1.getAddress().getArea())).thenReturn(customerList);
		assertThat(service.getCustomerByCity(add1.getArea())).isEqualTo(customerList);
		
	}
	
	
}

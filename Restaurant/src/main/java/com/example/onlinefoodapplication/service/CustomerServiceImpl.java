package com.example.onlinefoodapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.FoodCart;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerAlreadyPresent;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInArea;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInCity;
import com.example.onlinefoodapplication.models.CartAdding;
import com.example.onlinefoodapplication.repo.CustomerRepository;
import com.example.onlinefoodapplication.repo.ItemRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private ItemRepository item_repo;
	
	@Override
	public Customer addCustomer(Customer cust) throws CustomerAlreadyPresent {
		Customer temp= repo.findById(cust.getCustomerId()).orElse(null);
		if(temp!=null) {
			throw new CustomerAlreadyPresent();
		}
		Customer new_cust=repo.save(cust);
		return new_cust;
	}

	@Override
	public Customer updateCustomer(Customer cust) throws CustomerNotPresent {
		Customer temp=repo.findById(cust.getCustomerId()).orElse(null);
		if(temp==null) {
			throw new CustomerNotPresent();
		}
		Customer new_cust=repo.save(cust);
		return new_cust;
	}

	@Override
	public Customer deleteCustomer(int id) throws CustomerNotPresent {
		Customer cust= repo.findById(id).orElse(null);
		if(cust==null) {
			throw new CustomerNotPresent();
		}
		
		repo.delete(cust);
		return cust;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return repo.findAll();
	}

	@Override
	public Customer getCustomerById(int id) throws CustomerNotPresent {
		Customer temp=repo.findById(id).orElse(null);
		if(temp==null) {
			throw new CustomerNotPresent();
		}
		return temp;
	}

	//remain
	@Override
	public Customer addItemToACart(CartAdding add) throws ItemNotPresent, CustomerNotPresent {
		
		Customer c=repo.findById(add.getId()).orElse(null);
		
		if(c==null) {
			throw new CustomerNotPresent("Customer is not present");
		}
		FoodCart cart=c.getCart();
		
		for(Item item:add.getList()) {
			System.out.println("impl item"+item_repo.findById(item.getItem_id()).orElse(null));
			if(item_repo.findById(item.getItem_id()).orElse(null)==null) {
				throw new ItemNotPresent("item not present");
			}
		}
		
		boolean a=cart.getItems().addAll(add.getList());
		if(!a) {
			return null;
		}
		c.setCart(cart);
		Customer temp=repo.save(c);
		return temp;
	}

	@Override
	public FoodCart getCart(int id) throws CustomerNotPresent {
		Customer cust=repo.findById(id).orElse(null);
		if(cust==null) {
			throw new CustomerNotPresent("Customer is not present");
		}
		return cust.getCart();
	}

	//remain
	@Override
	public Customer deleteItemFromCart(int cust, int item) throws ItemNotPresent, CustomerNotPresent, CartIsEmpty {
		Customer c=repo.findById(cust).orElse(null);
		if(c==null) {
			throw new CustomerNotPresent("Customer Not present");
		}
		
		//finding if the item to delete is present

		FoodCart cart=c.getCart();
		if(cart.getItems().size()==0) {
			throw new CartIsEmpty("Cart is empty");
		}
		for(Item items:cart.getItems()) {
			if(items.getItem_id()==item) {
				List<Item> list=cart
						.getItems()
						.stream()
						.filter(e->!(e.getItem_id()==item)).collect(Collectors.toList());
				cart.setItems(list);
				c.setCart(cart);
				return repo.save(c);
			}
		}
		throw new ItemNotPresent("Item is not present");
		
	}

	@Override
	public List<Customer> getCustomerByCity(String city_name) throws NoCustomerPresentInCity {
		List<Customer> list=repo.getCustomerByCity(city_name);
		if(list.size()==0) {
			throw new NoCustomerPresentInCity("No customer present");
		}
		return list;
	}

	@Override
	public List<Customer> getCustomerByArea(String area_name) throws NoCustomerPresentInArea {
		List<Customer> list=repo.getCustomerByArea(area_name);
		if(list.size()==0) {
			throw new NoCustomerPresentInArea("No customer present");
		}
		return list;
	}

}

package com.example.onlinefoodapplication.service;

import java.util.List;

import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.FoodCart;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerAlreadyPresent;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.ItemNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInArea;
import com.example.onlinefoodapplication.exceptionhandler.NoCustomerPresentInCity;
import com.example.onlinefoodapplication.exceptionhandler.NoRestaurantPresentInCity;
import com.example.onlinefoodapplication.models.CartAdding;

public interface CustomerService {

	Customer addCustomer(Customer cust) throws CustomerAlreadyPresent;

	Customer updateCustomer(Customer cust) throws CustomerNotPresent;

	Customer deleteCustomer(int id) throws CustomerNotPresent;

	List<Customer> getAllCustomers();

	Customer getCustomerById(int id) throws CustomerNotPresent;

	Customer addItemToACart(CartAdding add) throws ItemNotPresent, CustomerNotPresent;

	FoodCart getCart(int id) throws CustomerNotPresent;

	Customer deleteItemFromCart(int cust, int item) throws ItemNotPresent,CustomerNotPresent, CartIsEmpty;

	List<Customer> getCustomerByCity(String city_name) throws NoCustomerPresentInCity;

	List<Customer> getCustomerByArea(String area_name) throws  NoCustomerPresentInArea;

}

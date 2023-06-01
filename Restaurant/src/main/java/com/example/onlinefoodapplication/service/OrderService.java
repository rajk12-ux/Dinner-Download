package com.example.onlinefoodapplication.service;

import java.util.List;

import com.example.onlinefoodapplication.entities.OrderDetail;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoOrderPlacedByCustomer;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchOrder;
import com.example.onlinefoodapplication.models.CustomerOrderView;
import com.example.onlinefoodapplication.models.RestaurantOrderView;

public interface OrderService {

	OrderDetail addOrder(int cust_id) throws CustomerNotPresent,CartIsEmpty;

	int cancelOrder(int id) throws NoSuchOrder;

	List<CustomerOrderView> getOrderByCustomerId(int id) throws NoOrderPlacedByCustomer;
	
	List<RestaurantOrderView> getOrdersForARestaurant(int id) throws NoOrderPlacedByCustomer;
}

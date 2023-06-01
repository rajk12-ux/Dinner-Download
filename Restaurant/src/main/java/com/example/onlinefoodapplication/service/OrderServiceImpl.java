package com.example.onlinefoodapplication.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.Item;
import com.example.onlinefoodapplication.entities.OrderDetail;
import com.example.onlinefoodapplication.entities.OrderedItems;
import com.example.onlinefoodapplication.entities.Restaurant;
import com.example.onlinefoodapplication.exceptionhandler.CartIsEmpty;
import com.example.onlinefoodapplication.exceptionhandler.CustomerNotPresent;
import com.example.onlinefoodapplication.exceptionhandler.NoOrderPlacedByCustomer;
import com.example.onlinefoodapplication.exceptionhandler.NoSuchOrder;
import com.example.onlinefoodapplication.models.CustomerOrderView;
import com.example.onlinefoodapplication.models.RestaurantOrderView;
import com.example.onlinefoodapplication.repo.CustomerRepository;
import com.example.onlinefoodapplication.repo.OrderRepository;
import com.example.onlinefoodapplication.repo.RestaurantRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private static final String ORDER_CREATED = "NOT_DELIVERED";

	@Autowired
	private CustomerRepository cust_repo;

	@Autowired
	private RestaurantRepository rest_repo;

	@Autowired
	private OrderRepository repo;

	@Override
	public OrderDetail addOrder(int cust_id) throws CustomerNotPresent, CartIsEmpty {
	
		Customer c = cust_repo.findById(cust_id).orElse(null);
		
		if (c == null) {
			throw new CustomerNotPresent("Customer is not present");
		}

		if (c.getCart().getItems().size() == 0) {
			throw new CartIsEmpty("No Item in cart");
		}

		// generating date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime time = LocalDateTime.now();

		// generating total cost
		Integer cost = c.getCart().getItems().stream().map(e -> e.getCost()).reduce(0, (a, b) -> a + b);
		
		// generating number of items
		Integer total = c.getCart().getItems().size();

		
//		 generating orderedItems
		List<OrderedItems> or_list = c.getCart().getItems().stream()
				.map(e -> {
			return new OrderedItems(e.getItem_id(), e.getName(), e.getCost());
		}).collect(Collectors.toList());
		
		// Creating order object
		OrderDetail order = new OrderDetail();
		order.setActive(true);
		order.setCustomer_id(c.getCustomerId());
		order.setCart_id(c.getCart().getCart_id());
		order.setOrderDateAndTime(time.format(formatter));
		order.setTotalAmount(cost);
		order.setTotalItems(total);
		order.setOrderStatus(ORDER_CREATED);
		order.setOrderdItem(or_list);
		
		OrderDetail f=repo.save(order);
		System.out.println(f);
		return f;
	}

	@Override
	public int cancelOrder(int id) throws NoSuchOrder {
		OrderDetail order = repo.findById(id).orElse(null);
		if (order == null) {
			throw new NoSuchOrder();
		}
		repo.delete(order);
		return 0;
	}

	@Override
	public List<CustomerOrderView> getOrderByCustomerId(int id) throws NoOrderPlacedByCustomer {
		List<OrderDetail> detaillist = repo.findAll();
		
		if (detaillist.size() == 0) {
			throw new NoOrderPlacedByCustomer();
		}


		List<CustomerOrderView> list = detaillist.stream().filter(e -> e.getCustomer_id() == id).map(d -> {
			return new CustomerOrderView(d.getOrder_id(), d.getOrderdItem());
		}).collect(Collectors.toList());
				
		if (list.size() == 0) {
			throw new NoOrderPlacedByCustomer();
		}

		return list;
	}

	@Override
	public List<RestaurantOrderView> getOrdersForARestaurant(int id) throws NoOrderPlacedByCustomer {
		
		List<RestaurantOrderView> populate=new ArrayList<RestaurantOrderView>();
		
		List<OrderDetail> detailList=repo.findAll();
		if(detailList.size()==0) {
			throw new NoOrderPlacedByCustomer();
		}
		
		List<Restaurant> restaurant=rest_repo.findAll();

		
		
		for (OrderDetail detail : detailList) {
			List<OrderedItems> item=detail.getOrderdItem();
			
			for (OrderedItems or_items : item) {
				
				for (Restaurant restaurant2 : restaurant) {
				
					List<Item> rest_items=restaurant2.getItems();
					
					for (Item actual : rest_items) {
						
						if(actual.getItem_id()==or_items.getItem_id() && restaurant2.getRest_id()==id) {
							if(populate.isEmpty()) 
							{
									RestaurantOrderView view =new RestaurantOrderView(restaurant2.getRest_id(),
									restaurant2.getName(),
									new ArrayList<OrderedItems>(Arrays.asList(new OrderedItems[] {or_items})));
									
									populate.add(view);
									
							} else {
								
								populate.get(0).getOrderedlist().add(or_items);
							}
						} 
						
					}
					
				}
			}
			
		}
		return populate;
		
	}

}

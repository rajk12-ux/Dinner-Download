package com.example.onlinefoodapplication.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = CustomerAlreadyPresent.class)
	public ResponseEntity<String> cutomerPresentExcetion(CustomerAlreadyPresent e){
		return new ResponseEntity<String>("Customer is already present", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = CustomerNotPresent.class)
	public ResponseEntity<String> cutomerPresentExcetion(CustomerNotPresent e){
		return new ResponseEntity<String>("Customer is not present", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = ItemNotPresent.class)
	public ResponseEntity<String> ItemNotPresentExcetion(ItemNotPresent e){
		return new ResponseEntity<String>("Requested item is not present", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = CartIsEmpty.class)
	public ResponseEntity<String> cartIsEmptyExcetion(CartIsEmpty e){
		return new ResponseEntity<String>("Cart is Empty", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NoSuchOrder.class)
	public ResponseEntity<String> noSuchOrderExcetion(NoSuchOrder e){
		return new ResponseEntity<String>("There is no such order placed", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NoOrderPlacedByCustomer.class)
	public ResponseEntity<String> noOrderPlacedByCustomer(NoOrderPlacedByCustomer e){
		return new ResponseEntity<String>("There is no order placed by this customer", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NoSuchRestaurant.class)
	public ResponseEntity<String> noSuchRestaurant(NoSuchRestaurant e){
		return new ResponseEntity<String>("There is no such restaurant", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NoItemsInRestaurant.class)
	public ResponseEntity<String> noItemsInRestaurant(NoItemsInRestaurant e){
		return new ResponseEntity<String>("This restaurant has no items to serve", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NoRestaurantPresentInCity.class)
	public ResponseEntity<String> noRestaurantPresentInCity(NoRestaurantPresentInCity e){
		return new ResponseEntity<String>("There is no restaurant present in this city.", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NoRestaurantPresentInArea.class)
	public ResponseEntity<String> noRestaurantPresentInArea(NoRestaurantPresentInArea e){
		return new ResponseEntity<String>("There is no restaurant present in this area.", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = ItemNotPresentInRestaurant.class)
	public ResponseEntity<String> itemNotPresentInRestaurant(ItemNotPresentInRestaurant e){
		return new ResponseEntity<String>("The specified item is not present in restaurant.", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = ItemCannotBeDeleted.class)
	public ResponseEntity<String> itemCannotBeDeleted(ItemCannotBeDeleted e){
		return new ResponseEntity<String>("Item cannot be deleted as there is only one item in restaurant.", HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(value = NoCustomerPresentInArea.class)
	public ResponseEntity<String> noCustomerPresentInArea(NoCustomerPresentInArea e){
		return new ResponseEntity<String>("No customer present in this area", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NoCustomerPresentInCity.class)
	public ResponseEntity<String> noCustomerPresentInCity(NoCustomerPresentInCity e){
		return new ResponseEntity<String>("No customer present in this city", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = NotLoggedIn.class)
	public ResponseEntity<String> notLoggedIn(NotLoggedIn e){
		return new ResponseEntity<String>("Not logged in", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = OperationNotAllowed.class)
	public ResponseEntity<String> operationNotAllowed(OperationNotAllowed e){
		return new ResponseEntity<String>("This operation is not allowed", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = UserNotFound.class)
	public ResponseEntity<String> userNotFound(UserNotFound e){
		return new ResponseEntity<String>("User not found", HttpStatus.CONFLICT);
	}
	
	

}


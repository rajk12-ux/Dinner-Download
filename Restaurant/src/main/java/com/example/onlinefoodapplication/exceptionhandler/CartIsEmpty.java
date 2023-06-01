package com.example.onlinefoodapplication.exceptionhandler;

public class CartIsEmpty extends Exception {
	String message;

	public CartIsEmpty() {
		super();
		
	}

	public CartIsEmpty(String message) {
		super();
		this.message=message;
	}
	

}

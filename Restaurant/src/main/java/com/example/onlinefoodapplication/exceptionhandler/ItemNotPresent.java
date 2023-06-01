package com.example.onlinefoodapplication.exceptionhandler;

public class ItemNotPresent extends Exception {
	String message;

	public ItemNotPresent() {
		super();
		
	}

	public ItemNotPresent(String message) {
		super();
		this.message=message;
	}
	

}

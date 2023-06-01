package com.example.onlinefoodapplication.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NoOrderPlacedByCustomer extends Exception {
	String message;
}

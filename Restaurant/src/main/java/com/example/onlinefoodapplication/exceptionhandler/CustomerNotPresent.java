package com.example.onlinefoodapplication.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerNotPresent extends Exception {
	String message;
}

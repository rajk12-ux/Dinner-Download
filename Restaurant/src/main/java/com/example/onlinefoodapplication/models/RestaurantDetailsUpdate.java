package com.example.onlinefoodapplication.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class RestaurantDetailsUpdate {
	
	int id;//restid
	String name;
	String managerName;
	String contact;
	

}

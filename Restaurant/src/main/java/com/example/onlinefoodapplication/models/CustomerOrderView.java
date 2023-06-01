package com.example.onlinefoodapplication.models;

import java.util.List;

import com.example.onlinefoodapplication.entities.OrderedItems;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustomerOrderView {
	
	int id;//customerid
	List<OrderedItems> list;
}

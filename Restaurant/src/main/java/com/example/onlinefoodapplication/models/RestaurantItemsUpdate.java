package com.example.onlinefoodapplication.models;

import java.util.List;

import com.example.onlinefoodapplication.entities.Item;

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
public class RestaurantItemsUpdate {
	int rest_id;
	List<Item> list;

}

package com.example.onlinefoodapplication.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "foodcart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodCart {
	
	@Id
	private int cart_id;
	
	@ManyToMany
	private List<Item> items;
}

package com.example.onlinefoodapplication.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ordereditems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderedItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int or_id;
	
	private int item_id;
	private String name;
	private int cost;
	public OrderedItems(int item_id, String name, int cost) {
		super();
		this.item_id = item_id;
		this.name = name;
		this.cost = cost;
	}
	
	
}

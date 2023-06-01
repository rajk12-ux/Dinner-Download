package com.example.onlinefoodapplication.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name = "restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Restaurant {
	
	@Id
	private int rest_id;
	
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Rating rating;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Item> items;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	private String managerName;
	
	private String contactNumber;
	
	

}

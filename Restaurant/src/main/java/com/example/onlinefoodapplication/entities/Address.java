package com.example.onlinefoodapplication.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {
	
	@Id
	private int add_id;

	private String buildingName;
	private int roomNo;
	private String streetNo;
	private String area;
	private String city;
	private String state;
	private String country;
	private String pincode;

}

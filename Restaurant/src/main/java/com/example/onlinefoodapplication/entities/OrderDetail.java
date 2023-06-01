package com.example.onlinefoodapplication.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orderdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int order_id;

	@Column(name = "customer_id", nullable = false)
	private int customer_id;

	@Column(name = "cart_id", nullable = false)
	private int cart_id;

	@Column(name = "orderdateandtime")
	private String orderDateAndTime;

	@Column(name = "totalamount", nullable = false)
	private int totalAmount;

	@Column(name = "totalitems", nullable = false)
	private int totalItems;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name = "orderdetails_ordereditems",
			joinColumns = {@JoinColumn(name = "orderdetailid")},
            inverseJoinColumns = {@JoinColumn(name = "orderitemsid")}		
	)
	private List<OrderedItems> orderdItem;

	private String orderStatus;

	private boolean isActive = true;
}

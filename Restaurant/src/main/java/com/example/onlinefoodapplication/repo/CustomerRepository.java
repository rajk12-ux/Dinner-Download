package com.example.onlinefoodapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.onlinefoodapplication.entities.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select e from Customer e inner join e.address a where a.city=:c")
	List<Customer> getCustomerByCity(@Param("c") String city);
	
	@Query("select e from Customer e inner join e.address a where a.area=:are")
	List<Customer> getCustomerByArea(@Param("are") String area);
}

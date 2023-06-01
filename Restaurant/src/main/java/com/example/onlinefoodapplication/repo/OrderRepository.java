package com.example.onlinefoodapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.onlinefoodapplication.entities.OrderDetail;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Integer> {

}

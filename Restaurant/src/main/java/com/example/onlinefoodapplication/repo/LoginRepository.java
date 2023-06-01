package com.example.onlinefoodapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.onlinefoodapplication.entities.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {

}

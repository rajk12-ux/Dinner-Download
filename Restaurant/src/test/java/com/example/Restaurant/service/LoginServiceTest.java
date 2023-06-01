package com.example.Restaurant.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.onlinefoodapplication.entities.Login;
import com.example.onlinefoodapplication.repo.LoginRepository;
import com.example.onlinefoodapplication.service.LoginService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class LoginServiceTest {

	@Autowired
	private LoginService service;
	
	@MockBean
	private LoginRepository repo;
	
	@Test
	public void signInTest() {	
		when(repo.findById(100)).thenReturn(Optional.ofNullable(new Login(100, "restowner","1234",false,true)));		
		assertEquals(new Login(100, "restowner","1234",true,true), service.signIn(new Login(100, "restowner","1234",false,true)));
	}
	@Test
	public void signInTest1() {
		when(repo.findById(101)).thenReturn(Optional.ofNullable(new Login(101, "normaluser","1234",false,false)));		
		assertEquals(new Login(101, "normaluser","1234",true,false), service.signIn(new Login(101, "normaluser","1234",false,false)));
	}
	
	@Test
	public void signInTest2() {			
		assertNull(service.signIn(new Login(102, "restowner","1234",false,false)));
	}
	
	@Test
	public void signOutTest() {
		when(repo.findById(100)).thenReturn(Optional.ofNullable(new Login(100, "restowner","1234",true,true)));
		assertEquals(new Login(100, "restowner","1234",false,true), service.signOut(new Login(100, "restowner","1234",true,true)));
	}
	
	@Test
	public void signOutTest1() {
		when(repo.findById(101)).thenReturn(Optional.ofNullable(new Login(101, "normaluser","1234",true,false)));		
		assertEquals(new Login(101, "normaluser","1234",false,false), service.signOut(new Login(101, "normaluser","1234",true,false)));
	}
}

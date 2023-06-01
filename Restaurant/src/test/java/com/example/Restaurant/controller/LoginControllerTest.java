package com.example.Restaurant.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.example.onlinefoodapplication.controller.LoginController;
import com.example.onlinefoodapplication.entities.Customer;
import com.example.onlinefoodapplication.entities.Login;
import com.example.onlinefoodapplication.exceptionhandler.NotLoggedIn;
import com.example.onlinefoodapplication.exceptionhandler.UserNotFound;
import com.example.onlinefoodapplication.service.LoginService;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	@InjectMocks
	private LoginController controller;
	
	@Mock
	private LoginService service;
	
	
	Login login;
	MockHttpServletRequest request;
	@BeforeEach
	void setUp() throws Exception {
		request = new MockHttpServletRequest();
		login= new Login();
		login.setLoggedIn(false);
		login.setOwner(false);
		login.setUserid(100);
		login.setUserName("normaluser");
		login.setPassword("1234");
	
		HttpSession session=request.getSession(true);
		session.setAttribute("userDetails", login);
	}
	
	@Test
	@DisplayName("SignIn Test")
	void testSignIn() throws UserNotFound {
		
		Login login1= new Login();
		login1.setLoggedIn(true);
		login1.setOwner(false);
		login1.setUserid(100);
		login1.setUserName("normaluser");
		login1.setPassword("1234");
		when(service.signIn(ArgumentMatchers.any(Login.class))).thenReturn(login1);

		assertEquals(new ResponseEntity<String>("LOGGED_IN", HttpStatus.FOUND), controller.signIn(login, request));

	}
	@Test
	@DisplayName("SignIn Test")
	void testSignIn2() throws UserNotFound {
		
		
		when(service.signIn(ArgumentMatchers.any(Login.class))).thenReturn(null);

		assertThrows(UserNotFound.class,()-> controller.signIn(login, request));

	}
	
	
	@Test
	@DisplayName("SignIn Test")
	void testSignIn3() throws UserNotFound {		
		
		when(service.signIn(ArgumentMatchers.any(Login.class))).thenReturn(login);
		assertEquals(new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND), controller.signIn(login, request));

	}

	
	@Test
	@DisplayName("SignOut Test")
	void testSignOut() throws UserNotFound {		
		
		Login login1= new Login();
		login1.setLoggedIn(false);
		login1.setOwner(false);
		login1.setUserid(100);
		login1.setUserName("normaluser");
		login1.setPassword("1234");
		
		when(service.checkSession(request)).thenReturn(false);

		assertThrows(NotLoggedIn.class,()-> controller.signOut(request));

	}


	@Test
	@DisplayName("SignOut Test 2")
	void testSignOut2() throws UserNotFound, NotLoggedIn {		
		
		Login login1= new Login();
		login1.setLoggedIn(true);
		login1.setOwner(false);
		login1.setUserid(100);
		login1.setUserName("normaluser");
		login1.setPassword("1234");
		
		
	
		
		when(service.checkSession(request)).thenReturn(true);
		
		assertEquals(new ResponseEntity<String>("LOGGED_OUT", HttpStatus.OK), controller.signOut(request));
		
	}
	
}

package com.example.onlinefoodapplication.service;

import javax.servlet.http.HttpServletRequest;

import com.example.onlinefoodapplication.entities.Login;

public interface LoginService {
	public Login signIn(Login login);

	public Login signOut(Login login);

	public boolean checkSession(HttpServletRequest request);
}

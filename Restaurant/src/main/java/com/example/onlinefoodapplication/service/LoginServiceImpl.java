package com.example.onlinefoodapplication.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinefoodapplication.entities.Login;
import com.example.onlinefoodapplication.repo.LoginRepository;


@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginRepository repo;

	@Override
	public Login signIn(Login login) {
		
		Login repoObj = repo.findById(login.getUserid()).orElse(null);
		if(repoObj==null) {
			return null;
		}
		if (login.getPassword().equals(repoObj.getPassword())) {
			
			repoObj.setLoggedIn(true);
			login.setLoggedIn(true);
			repo.save(repoObj);
			return repoObj;
		}
		return null;
	}

	@Override
	public Login signOut(Login login) {
		Login repoObj = repo.findById(login.getUserid()).get();
		if(repoObj==null) {
			return null;
		}
		if (login.getPassword().equals(repoObj.getPassword())) {
			repoObj.setLoggedIn(false);
			login.setLoggedIn(false);
			repo.save(repoObj);
			return repoObj;
		}
		return null;
	}

	@Override
	public boolean checkSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			Login currentUser = (Login) session.getAttribute("userDetails");
			if (currentUser.isLoggedIn()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}

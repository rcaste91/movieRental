package com.rcaste.movieRental.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rcaste.movieRental.models.Blacklist;
import com.rcaste.movieRental.repositories.BlacklistRepository;

@RestController
public class AccountAdminController {

	@Autowired
	BlacklistRepository bRepository;
	
	public AccountAdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/ulogout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		
		String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		
		Blacklist blackToken = new Blacklist(jwtToken);
		bRepository.saveAndFlush(blackToken);
		
		return "User logged out succesfully";
	}
	

}

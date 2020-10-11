package com.rcaste.movieRental.controllers.errors;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5119307597126735081L;

	
	public UserNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
		super("User not found: "+id);
	}
}

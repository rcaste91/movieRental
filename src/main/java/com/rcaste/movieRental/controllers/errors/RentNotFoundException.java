package com.rcaste.movieRental.controllers.errors;

public class RentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -721249594896220101L;

	public RentNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
		super("Rent not found: "+id);
	}
}

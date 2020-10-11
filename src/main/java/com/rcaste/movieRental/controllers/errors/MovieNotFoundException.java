package com.rcaste.movieRental.controllers.errors;

public class MovieNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1114713151562676792L;

	public MovieNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
		super("Movie not found: "+id);
	}
}

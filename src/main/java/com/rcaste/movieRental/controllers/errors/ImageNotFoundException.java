package com.rcaste.movieRental.controllers.errors;

public class ImageNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5265410422597280299L;

	public ImageNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
		super("Image not found: "+id);
	}
}

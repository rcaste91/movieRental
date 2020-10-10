package com.rcaste.movieRental.models.requests;
import java.io.Serializable;

public class JwtResponse implements Serializable {


	private static final long serialVersionUID = 6227370385874037994L;

	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
}

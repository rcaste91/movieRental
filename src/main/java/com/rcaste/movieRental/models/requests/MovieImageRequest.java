package com.rcaste.movieRental.models.requests;

public class MovieImageRequest {

	private String  image;
	
	public MovieImageRequest() {
		
	}
	
	public MovieImageRequest(String image) {
		this.image=image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}

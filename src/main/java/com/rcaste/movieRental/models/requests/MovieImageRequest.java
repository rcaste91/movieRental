package com.rcaste.movieRental.models.requests;

public class MovieImageRequest {

	private int imageId;
	private String  image;
	
	public MovieImageRequest() {
		
	}
	
	public MovieImageRequest(String image) {
		this.image=image;
	}
	
	public MovieImageRequest(int id ,String image) {
		this.imageId=id;
		this.image=image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	public int getImageId() {
		return imageId;
	}



	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

}

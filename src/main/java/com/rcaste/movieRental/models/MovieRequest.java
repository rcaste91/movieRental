package com.rcaste.movieRental.models;

import java.util.List;

public class MovieRequest {

	private Long movieId;
	private String title;
	private String description;
	private int stock;
	private float rentalPrice;
	private float salePrice;
	private String availability;
	private List<MovieImageRequest> images;
	
	public MovieRequest() {
		
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public float getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(float rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public List<MovieImageRequest> getImages() {
		return images;
	}

	public void setImages(List<MovieImageRequest> images) {
		this.images = images;
	}


		
}

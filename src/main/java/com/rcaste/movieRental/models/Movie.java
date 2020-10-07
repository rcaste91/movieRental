package com.rcaste.movieRental.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "movie")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
	private Long movieId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "rental_price")
	private float rentalPrice;
	
	@Column(name = "sale_price")
	private float salePrice;
	
	@Column(name = "availability")
	private String availability;
	
	@OneToMany(mappedBy = "movie")
	private List<MovieImage> movieImages;
	
	@OneToMany(mappedBy = "movie")
	private List<MovieLog> movieLog;
	
	@OneToMany(mappedBy = "movie")
	private List<MovieLike> movieLike;
	
	@OneToMany(mappedBy = "movie")
	private List<Rent> rents;
	
	@OneToMany(mappedBy = "movie")
	private List<Sale> sales;
	
	public Movie() {
		
	}
	
	
	public List<Rent> getRents() {
		return rents;
	}


	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}


	public List<Sale> getSales() {
		return sales;
	}


	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}


	public List<MovieLog> getMovieLog() {
		return movieLog;
	}



	public void setMovieLog(List<MovieLog> movieLog) {
		this.movieLog = movieLog;
	}



	public List<MovieLike> getMovieLike() {
		return movieLike;
	}



	public void setMovieLike(List<MovieLike> movieLike) {
		this.movieLike = movieLike;
	}



	public List<MovieImage> getMovieImages() {
		return movieImages;
	}




	public void setMovieImages(List<MovieImage> movieImages) {
		this.movieImages = movieImages;
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

	
}

package com.rcaste.movieRental.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "movie_log")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MovieLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_log_id")
	private Long movieLogId;
	
	@Column(name = "movie_id")
	private int movie;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "rental_price")
	private float rentalPrice;
	
	@Column(name = "sale_price")
	private float salePrice;
	
	@Column(name = "change_date")
	private Date date;
	
	
	public MovieLog() {
		
	}


	public Long getMovieLogId() {
		return movieLogId;
	}


	public void setMovieLogId(Long movieLogId) {
		this.movieLogId = movieLogId;
	}


	public int getMovie() {
		return movie;
	}


	public void setMovie(int movie) {
		this.movie = movie;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	
	
}

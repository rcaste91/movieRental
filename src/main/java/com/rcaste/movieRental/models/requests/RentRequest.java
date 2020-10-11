package com.rcaste.movieRental.models.requests;

import java.util.Date;

public class RentRequest {
	
	private int rentId;
	private int userId;
	private int movieId;
	private String rentDate;
	private String dateReturn;
	private String actualReturn;
	private float subtotal;
	private float total;
	
	public RentRequest() {
		
	}

	public int getRentId() {
		return rentId;
	}

	public void setRentId(int rentId) {
		this.rentId = rentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(String dateReturn) {
		this.dateReturn = dateReturn;
	}

	public String getActualReturn() {
		return actualReturn;
	}

	public void setActualReturn(String actualReturn) {
		this.actualReturn = actualReturn;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getRentDate() {
		return rentDate;
	}

	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}

	
}

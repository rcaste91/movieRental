package com.rcaste.movieRental.models.requests;

import java.util.Date;

public class RentRequest {
	
	private int rentId;
	private int userId;
	private int movieId;
	private Date rentDate;
	private Date dateReturn;
	private Date actualReturn;
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

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public Date getActualReturn() {
		return actualReturn;
	}

	public void setActualReturn(Date actualReturn) {
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

	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	
}

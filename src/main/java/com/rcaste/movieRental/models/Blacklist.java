package com.rcaste.movieRental.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "blacklist")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Blacklist {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expired_id")
	private Long expiredId;
	
	@Column(name = "expired_token")
	private String expiredToken;
	
	public Blacklist() {
		
	}
	
	public Blacklist(String expiredToken) {
		this.expiredToken=expiredToken;
	}

	public Long getExpiredId() {
		return expiredId;
	}

	public void setExpiredId(Long expiredId) {
		this.expiredId = expiredId;
	}

	public String getExpiredToken() {
		return expiredToken;
	}

	public void setExpiredToken(String expiredToken) {
		this.expiredToken = expiredToken;
	}
	
	
}

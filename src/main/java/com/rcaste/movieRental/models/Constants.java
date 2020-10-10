package com.rcaste.movieRental.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "constants")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Constants {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "constant_id")
	private Long constantId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "value_c")
	private float valueC;
	
	
	public Constants() {
		
	}


	public Long getConstantId() {
		return constantId;
	}


	public void setConstantId(Long constantId) {
		this.constantId = constantId;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public float getValueC() {
		return valueC;
	}


	public void setValueC(float valueC) {
		this.valueC = valueC;
	}
	
	
}

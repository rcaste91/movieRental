package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.Rent;

public interface RentRepository extends JpaRepository<Rent, Long>{

}

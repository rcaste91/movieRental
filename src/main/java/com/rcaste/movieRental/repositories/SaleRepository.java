package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{

}

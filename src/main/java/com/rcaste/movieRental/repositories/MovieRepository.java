package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

}

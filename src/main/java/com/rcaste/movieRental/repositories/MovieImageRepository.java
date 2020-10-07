package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

}

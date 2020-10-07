package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.MovieLog;

public interface MovieLogRepository extends JpaRepository<MovieLog, Long>{

}

package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.MovieLike;

public interface MovieLikeRepository extends JpaRepository<MovieLike, Long> {

}

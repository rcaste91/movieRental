package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rcaste.movieRental.models.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

	@Query(
			value= "delete from movie_image where movie_id = :movieId", nativeQuery = true
			)
	void deleteAllMovieImages(@Param("movieId") int id);
}

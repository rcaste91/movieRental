package com.rcaste.movieRental.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rcaste.movieRental.models.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

	@Query(
			value="select * from movie_image where movie_id = :movieid", nativeQuery=true
			)
	List<MovieImage>getImagesByMovieId(@Param("movieid") int movieid);

}

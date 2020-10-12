package com.rcaste.movieRental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rcaste.movieRental.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

	List<Movie> findByAvailabilityOrderByTitleAsc (String aval);
		
	@Query("select s from Movie s where UPPER(s.title) like %:name%")
	List<Movie> findByTitleL (@Param("name")String title);

	
}

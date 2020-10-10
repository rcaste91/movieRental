package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rcaste.movieRental.models.Constants;

public interface ConstantsRepository extends JpaRepository<Constants, Long>{
	
	@Query(
			value="select value_c from constants where name = :name ", nativeQuery=true
			)
	float findDelay(@Param("name") String name);
	

}

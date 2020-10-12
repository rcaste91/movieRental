package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.Blacklist;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long>{

	Blacklist findByExpiredToken(String token);
}

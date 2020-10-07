package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{

}

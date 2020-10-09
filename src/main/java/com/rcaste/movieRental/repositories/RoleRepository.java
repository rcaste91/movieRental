package com.rcaste.movieRental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.Role;
import com.rcaste.movieRental.models.Users;

public interface RoleRepository extends JpaRepository<Role, Long>{

	List<Role> findByUsers(Users user);
	
}

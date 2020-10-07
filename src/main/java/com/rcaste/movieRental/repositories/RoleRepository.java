package com.rcaste.movieRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcaste.movieRental.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}

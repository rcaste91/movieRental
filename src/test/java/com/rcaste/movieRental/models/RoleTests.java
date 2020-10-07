package com.rcaste.movieRental.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rcaste.movieRental.repositories.RoleRepository;

@SpringBootTest
public class RoleTests {
	
	@Autowired
    private RoleRepository repository;

    @PersistenceContext
    private EntityManager entityManager;
	
	@Test
	void testAddRole() {
		
		Role r= new Role();
		r.setName("TestRole");
		repository.saveAndFlush(r);
		
		Role rx = new Role();
		rx = repository.getOne(r.getRoleId());
		
		entityManager.clear();
		
		assertEquals("TestRole",rx.getName());
		
		repository.deleteById(r.getRoleId());
		
		
	}

}

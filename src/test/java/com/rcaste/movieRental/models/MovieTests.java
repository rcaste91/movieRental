package com.rcaste.movieRental.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rcaste.movieRental.logic.MovieLogic;
import com.rcaste.movieRental.repositories.MovieImageRepository;
import com.rcaste.movieRental.repositories.MovieLogRepository;
import com.rcaste.movieRental.repositories.MovieRepository;

@SpringBootTest
public class MovieTests {
	
	@Autowired
    private MovieRepository repository;
	@Autowired
    private MovieLogRepository Lrepository;
	@Autowired
    private MovieImageRepository Irepository;
	
	
	private MovieLogic logic;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public MovieTests() {
		// TODO Auto-generated constructor stub
		logic=new MovieLogic();
	}
	
	@Test
	void testCreateLogEntry() {
		Movie m = repository.findAll().get(0);
		MovieLog ml = logic.logUpdate(m);
		MovieLog mtest= Lrepository.saveAndFlush(ml);
		
		entityManager.clear();
		
		assertEquals("Titanic",mtest.getTitle());
		
		//Lrepository.deleteById(mtest.getMovieLogId());
		
	}

}

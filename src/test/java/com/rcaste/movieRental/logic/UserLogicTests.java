package com.rcaste.movieRental.logic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rcaste.movieRental.models.Movie;

@SpringBootTest
public class UserLogicTests {

	private MovieUserLogic logic;
	
	public UserLogicTests() {
		// TODO Auto-generated constructor stub
		logic=new MovieUserLogic();
	}
	
	@Test
	void testCalculateRentPrice() {
		
		Movie m = createTestMovie();
		
	}
	
	Movie createTestMovie() {
		
		Movie m = new Movie();
		m.setMovieId((long) 1);
		m.setAvailability("y");
		m.setDescription("test");
		m.setRentalPrice((float) 1.0);
		m.setSalePrice((float) 1.0);
		m.setStock(1);
		m.setTitle("test");
		
		return m;
	}
	
}

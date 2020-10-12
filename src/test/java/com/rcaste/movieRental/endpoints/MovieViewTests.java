package com.rcaste.movieRental.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.rcaste.movieRental.controllers.MovieViewController;
import com.rcaste.movieRental.models.Movie;

@SpringBootTest
public class MovieViewTests {

	MovieViewController cont;
	
	public MovieViewTests() {
		cont=new MovieViewController();
	}
	
	@Test
	void testSearchName() {
		
		List<Movie> search=cont.searchMovieByName("MAN");
		
		assertTrue(search.size()>0);
	}
	
}

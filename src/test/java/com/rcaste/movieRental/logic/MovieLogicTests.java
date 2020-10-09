package com.rcaste.movieRental.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rcaste.movieRental.models.MovieImage;
import com.rcaste.movieRental.models.MovieImageRequest;
import com.rcaste.movieRental.models.MovieRequest;

@SpringBootTest
public class MovieLogicTests {

	private MovieLogic logic;
	
	public MovieLogicTests() {
		// TODO Auto-generated constructor stub
		logic=new MovieLogic(); 
	}
	
	@Test
	void testConvertImageList() {
		
		MovieRequest mr = new MovieRequest();
		mr.setTitle("Hannibal");
		mr.setAvailability("y");
		mr.setDescription("Movie about hannibal");
		mr.setRentalPrice((float) 2.8);
		mr.setSalePrice((float) 3.5);
		mr.setStock(3);
		
		List<MovieImageRequest> im = new ArrayList<>();
		im.add(new MovieImageRequest("url1"));
		im.add(new MovieImageRequest("url2"));
		
		mr.setImages(im);

		List<MovieImage> rx=logic.requestToMovieImage(logic.requestToMovie(mr), mr);
		
		assertTrue(rx.size()>0);
		
	}
}

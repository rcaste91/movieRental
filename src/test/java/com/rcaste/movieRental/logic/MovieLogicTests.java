package com.rcaste.movieRental.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieImage;
import com.rcaste.movieRental.models.MovieImageRequest;
import com.rcaste.movieRental.models.MovieLog;
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
		
		MovieRequest mr = createTestMovieRequest();
		
		List<MovieImageRequest> im = new ArrayList<>();
		im.add(new MovieImageRequest("url1"));
		im.add(new MovieImageRequest("url2"));
		
		mr.setImages(im);

		List<MovieImage> rx=logic.requestToMovieImage(logic.requestToMovie(mr), mr);
		
		assertTrue(rx.size()>0);
		
	}
	
	@Test
	void TestCreateLog() {
		
		Movie m = createTestMovie();
		MovieLog ml = logic.logUpdate(m);
		
		assertTrue(ml.getTitle().equals(m.getTitle()));
	}
	
	@Test
	void TestCreateMovieFromRequest() {
		
		MovieRequest mr = createTestMovieRequest();
		
		Movie m = logic.requestToMovie(mr);
		assertTrue(m.getTitle().equals(mr.getTitle()));
		
	}
	
	@Test
	void TestCreateMovieResponse() {
		
		Movie m = createTestMovie();
		List<MovieImage> l = new ArrayList<>();
		MovieImage i = new MovieImage();
		i.setMoviePhoto("foto1");
		MovieImage j = new MovieImage();
		i.setMoviePhoto("foto2");
		l.add(i);
		l.add(j);
		
		MovieRequest mr =logic.responseToMovie(m, l);
		
		assertTrue(mr.getImages().size()>0);
	}
	
	@Test
	void TestPrepareUpdate() {
		
		Movie m = createTestMovie();
		MovieRequest mr = createTestMovieRequest();
		
		Movie mx=logic.prepareUpdate(m, mr);
		
		assertTrue(mx.getTitle().equals(mr.getTitle()));
		
		
	}
	
	@Test
	void TestPrepareUpdateAval() {
		
		Movie m = createTestMovie();
		MovieRequest mr = createTestMovieRequest();
		
		Movie mx=logic.prepareUpdateAval(m, mr);
		
		assertTrue(mx.getAvailability().equals(mr.getAvailability()));
		
		
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
	
	MovieRequest createTestMovieRequest() {
		
		MovieRequest mr = new MovieRequest();
		mr.setTitle("Hannibal");
		mr.setAvailability("y");
		mr.setDescription("Movie about hannibal");
		mr.setRentalPrice((float) 2.8);
		mr.setSalePrice((float) 3.5);
		mr.setStock(3);
		
		return mr;
	}
}

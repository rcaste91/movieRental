package com.rcaste.movieRental.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.repositories.MovieRepository;

@RestController
@RequestMapping("api/v1/")
public class MovieViewController {

	@Autowired
	private MovieRepository mRepository;
	
	public MovieViewController() {
		// TODO Auto-generated constructor stub
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/all", method=RequestMethod.GET)
	public List<Movie> getMoviesByAval(@QueryParam("aval") String aval ) {
		
		List<Movie> movies = new ArrayList<Movie>();
		
		if( aval != null ) {
			
			if( (aval.equalsIgnoreCase("y")) || (aval.equalsIgnoreCase("n")) ) {
				movies=mRepository.findByAvailability(aval.toLowerCase());
			}
			
		}else {
			movies= mRepository.findAll();
		}
		
		return movies;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="movies", method=RequestMethod.GET)
	public List<Movie> getMoviesForUser( ) {
		
		List<Movie> movies = new ArrayList<Movie>();
		movies=mRepository.findByAvailability("y");
		
		return movies;
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/{name}", method=RequestMethod.GET)
	public List<Movie> searchMovieByName(@PathVariable String name){
		
		List<Movie> movies = new ArrayList<Movie>();
		
		movies=mRepository.findByTitleL(name.toUpperCase());
		
		return movies;
		
	}
}

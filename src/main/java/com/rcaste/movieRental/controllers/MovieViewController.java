package com.rcaste.movieRental.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.requests.MovieRequest;
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
	@RequestMapping(value="movies", method=RequestMethod.GET)
	public List<Movie> getAllMovies() {
		
		List<Movie> movies = new ArrayList<Movie>();
		return movies;
	}
}

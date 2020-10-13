package com.rcaste.movieRental.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
	
	private int PAGE_SIZE;
	
	public MovieViewController() {
		// TODO Auto-generated constructor stub
		PAGE_SIZE=5;
	}
	
	/**
	 * Vista de todas las peliculas paginadas y filtradas (opcional) por disponibilidad
	 * @param page numero de pagina de resultados
	 * @param aval filtro opcional de disponibilidad
	 * @return Lista de peliculas de acuerdo a filtro y pagina
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/all", method=RequestMethod.GET)
	public List<Movie> getMoviesByAval(@QueryParam("page") Integer page, @QueryParam("aval") String aval ) {
		
		List<Movie> movies = new ArrayList<Movie>();
		
		if(page == null || page<0 ) {
			page=0;
		}
		
		if( aval != null ) {
			
			if( (aval.equalsIgnoreCase("y")) || (aval.equalsIgnoreCase("n")) ) {
				movies = mRepository.findByAvailabilityOrderByTitleAsc(aval.toLowerCase(), PageRequest.of(page, PAGE_SIZE));
			}
			
		}else {
			movies= mRepository.findMoviesPag(PageRequest.of(page, PAGE_SIZE));
		}
		
		return movies;
	}
	
	/**
	 * Vista de peliculas paginadas con filtro opcional por LIKES
	 * @param page numero de pagina de resultados
	 * @param sort filtro para ordenar por LIKES
	 * @return Lista de peliculas de acuerdo a filtro y pagina
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="movies", method=RequestMethod.GET)
	public List<Movie> getMoviesForUser(@QueryParam("page") Integer page, @QueryParam("sort") String sort ) {
		
		List<Movie> movies = new ArrayList<Movie>();
		
		if(page == null || page<0) {
			page=0;
		}
		
		if( sort != null ) {
			
			if(sort.equalsIgnoreCase("likes")) {
				 movies = mRepository.findByAvailabilityOrderByTitleAsc("y", PageRequest.of(page, PAGE_SIZE)); 
				movies.sort( Comparator.comparingInt(Movie::getMovieLikeSize).reversed() );
			}
			
		}else {
			 movies = mRepository.findByAvailabilityOrderByTitleAsc("y", PageRequest.of(page, PAGE_SIZE));
		}
		
		return movies;
	}
	
	/**
	 * Busqueda de peliculas
	 * @param name nombre de MOVIE a buscar
	 * @return Lista de peliculas de acuerdo a busqueda
	 */
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/search/{name}", method=RequestMethod.GET)
	public List<Movie> searchMovieByName(@PathVariable String name){
		
		List<Movie> movies = new ArrayList<Movie>();
		
		movies=mRepository.findByTitleL(name.toUpperCase());
		
		return movies;
		
	}
	
	/**
	 * Lista de peliculas visible para usuarios no autenticados
	 * @return Lista de peliculas completa
	 */
	@RequestMapping(value="movies/public", method=RequestMethod.GET)
	public List<Movie> getAllMoviesPublic(){
		
		List<Movie> movies = new ArrayList<Movie>();
		
		movies= mRepository.findAll();
		movies.sort(Comparator.comparing(Movie::getTitle));
		
		return movies;
	}
}

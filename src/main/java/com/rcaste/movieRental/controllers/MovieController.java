package com.rcaste.movieRental.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rcaste.movieRental.logic.MovieLogic;
import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieImage;
import com.rcaste.movieRental.models.MovieRequest;
import com.rcaste.movieRental.repositories.MovieImageRepository;
import com.rcaste.movieRental.repositories.MovieLogRepository;
import com.rcaste.movieRental.repositories.MovieRepository;

@RestController
@RequestMapping("api/v1/")
public class MovieController {
	
	@Autowired
	private MovieRepository mRepository;
	
	@Autowired
	private MovieImageRepository iRepository;
	
	@Autowired
	private MovieLogRepository logRepository;
	
	private MovieLogic movieLogic;
	
	public MovieController() {
		// TODO Auto-generated constructor stub
		movieLogic=new MovieLogic();
	}
	
	/**
	 * Creacion de nueva pelicula, almacena la pelicula y sus imagenes
	 * @param movieRequest JSON entrada
	 * @return respuesta con nuevo ID
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies", method=RequestMethod.POST)
	public MovieRequest create(@RequestBody MovieRequest movieRequest) {
		
		MovieRequest response = new MovieRequest();
		
			Movie movieResponse = mRepository.saveAndFlush(movieLogic.requestToMovie(movieRequest));
			List<MovieImage> newImages = movieLogic.requestToMovieImage(movieResponse, movieRequest);
			saveImages(newImages);
			logRepository.saveAndFlush(movieLogic.logUpdate(movieResponse));
			response = movieLogic.responseToMovie(movieResponse,newImages);
				
		return response;
	}
	
	/**
	 * Actualizacion de una Movie
	 * @param id ID de pelicula a actualizar
	 * @param movieRequest JSON de entrada de pelicula
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/{id}", method=RequestMethod.PUT)
	public MovieRequest update(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
		
		MovieRequest response = new MovieRequest();
		Optional<Movie> searchMovie = mRepository.findById(id);
		
		if(searchMovie.get() != null) {
			Movie movieResponse = mRepository.saveAndFlush(movieLogic.prepareUpdate(searchMovie.get(), movieRequest));
			logRepository.saveAndFlush(movieLogic.logUpdate(movieResponse));
			response=movieLogic.responseToMovie(movieResponse, movieResponse.getMovieImages());
		}
		
		return response;
		
		
	}
	
	/**
	 * Cambiar campo AVAILABILITY
	 * @param id ID de pelicula a remover
	 * @param movieRequest	JSON de pelicula
	 * @return Movie actualizada unicamente con campo AVAILABILITY
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/aval/{id}", method=RequestMethod.PATCH)
	public MovieRequest remove(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
		
		MovieRequest response = new MovieRequest();
		
		Optional<Movie> searchMovie = mRepository.findById(id);
		if(searchMovie.get() != null) {
			Movie movieResponse = mRepository.saveAndFlush(movieLogic.prepareUpdateAval(searchMovie.get(), movieRequest));
			response=movieLogic.responseToMovie(movieResponse, movieResponse.getMovieImages());
		}
		
		return response;
	}
	
	/**
	 * Elimina una Movie y sus imagenes asociadas
	 * @param id ID de Movie a eliminar
	 * @return mensaje que confirma borrado
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/{id}", method=RequestMethod.DELETE)
	public MovieRequest delete(@PathVariable Long id) {
		
		MovieRequest response = new MovieRequest();
		
		Optional<Movie> searchMovie = mRepository.findById(id);
		if(searchMovie.get() != null) {
			
			//iRepository.deleteAllMovieImages(id.intValue());
			iRepository.deleteAll(searchMovie.get().getMovieImages());
			mRepository.delete(searchMovie.get());
			response.setTitle("Movie Deleted");
		}
		
		return response;
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies", method=RequestMethod.GET)
	public List<Movie> listMovies() {
	
		return mRepository.findAll();
	}
	
	private List<MovieImage> saveImages(List<MovieImage> images){
		
		List<MovieImage> imagesReturn = new ArrayList<MovieImage>();
		
		for (MovieImage movieImage : images) {
			imagesReturn.add(iRepository.saveAndFlush(movieImage));
		}
		
		return imagesReturn;
	}
	
	
	


}

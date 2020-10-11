package com.rcaste.movieRental.controllers;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rcaste.movieRental.controllers.errors.ImageNotFoundException;
import com.rcaste.movieRental.controllers.errors.MovieNotFoundException;
import com.rcaste.movieRental.logic.MovieLogic;
import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieImage;
import com.rcaste.movieRental.models.requests.MovieImageRequest;
import com.rcaste.movieRental.models.requests.MovieRequest;
import com.rcaste.movieRental.repositories.MovieImageRepository;
import com.rcaste.movieRental.repositories.MovieLogRepository;
import com.rcaste.movieRental.repositories.MovieRepository;

@RestController
@RequestMapping("api/v1/")
public class MovieAdminController {
	
	@Autowired
	private MovieRepository mRepository;
	
	@Autowired
	private MovieImageRepository iRepository;
	
	@Autowired
	private MovieLogRepository logRepository;
	
	private MovieLogic movieLogic;
	
	public MovieAdminController() {
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
		
		Movie searchMovie = mRepository.findById(id)
				.orElseThrow( () -> new MovieNotFoundException(id) );
		
		Movie movieResponse = mRepository.saveAndFlush(movieLogic.prepareUpdate(searchMovie, movieRequest) );
		logRepository.saveAndFlush(movieLogic.logUpdate(movieResponse));
		response=movieLogic.responseToMovie(movieResponse, movieResponse.getMovieImages());
		
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
		
		Movie searchMovie = mRepository.findById(id)
				.orElseThrow( () -> new MovieNotFoundException(id) );
		
		Movie movieResponse = mRepository.saveAndFlush(movieLogic.prepareUpdateAval(searchMovie, movieRequest));
		response=movieLogic.responseToMovie(movieResponse, movieResponse.getMovieImages());
		
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
		
		Movie searchMovie = mRepository.findById(id)
				.orElseThrow( () -> new MovieNotFoundException(id) );
			
			iRepository.deleteAll(searchMovie.getMovieImages());
			mRepository.delete(searchMovie);
			response.setTitle("Movie Deleted");
		
		
		return response;
	}
	
	
	/**
	 * Agrega nuevas imagenes para peliculas
	 * @param id ID de Movie a la cual agregar nuevas imagenes
	 * @param movieRequest JSON de entrada con url de nuevas imagenes
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/img/{id}", method=RequestMethod.PATCH)
	public MovieRequest addImage(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
		
		MovieRequest response = new MovieRequest();
		
		Movie searchMovie = mRepository.findById(id)
				.orElseThrow( () -> new MovieNotFoundException(id) );
		
		Movie movie = searchMovie;
		List<MovieImage> newImages = movieLogic.requestToMovieImage(movie, movieRequest);
		saveImages(newImages);
		newImages.clear();
		newImages=iRepository.getImagesByMovieId(id.intValue());
		response = movieLogic.responseToMovie(movie,newImages);
		
		return response;
	}
	
	/**
	 * Borra una imagen asociada a una Movie
	 * @param id ID de imagen 
	 * @return mensaje confirmando borrado de mensaje
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="movies/img/{id}", method=RequestMethod.DELETE)
	public MovieImageRequest deleteImage(@PathVariable Long id) {
		
		MovieImageRequest response = new MovieImageRequest();
		
		MovieImage image =iRepository.findById(id)
				.orElseThrow( () -> new ImageNotFoundException(id) );
		
		iRepository.delete(image);
		response.setImage("Image Deleted");
		
		
		return response;
	}
	
	/**
	 * Guarda Lista de imagenes en BD
	 * @param images lista de imagenes ya asociadas con una Movie
	 * @return Lista de imagenes almacenada para mostrar en confirmacion
	 */
	private List<MovieImage> saveImages(List<MovieImage> images){
		
		List<MovieImage> imagesReturn = new ArrayList<MovieImage>();
		
		for (MovieImage movieImage : images) {
			imagesReturn.add(iRepository.saveAndFlush(movieImage));
		}
		
		return imagesReturn;
	}
	
	
	
	
	


}

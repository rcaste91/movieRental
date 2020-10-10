package com.rcaste.movieRental.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieImage;
import com.rcaste.movieRental.models.MovieLog;
import com.rcaste.movieRental.models.requests.MovieImageRequest;
import com.rcaste.movieRental.models.requests.MovieRequest;

public class MovieLogic {
	
	public MovieLogic() {
		
	}
	
	/**
	 * Crea entidad de log para escribir en BD
	 * @param movie - pelicula actualizada en BD
	 * @return objeto MovieLog que sera escrito en BD
	 */
	public MovieLog logUpdate(Movie movie) {
		
		MovieLog log = new MovieLog();
		Date today = new Date();
		log.setMovie(movie.getMovieId().intValue());
		log.setRentalPrice(movie.getRentalPrice());
		log.setSalePrice(movie.getSalePrice());
		log.setTitle(movie.getTitle());
		log.setDate(today);
		
		return log;
	}

	/**
	 * Transforma JSON de entrada a entidad Movie para escribir en BD
	 * @param movieRequest - entidad mapeada de JSON de entrada
	 * @return entidad Movie a ser escrita en BD
	 */
	public Movie requestToMovie(MovieRequest movieRequest) {
	
	Movie movie = new Movie();
	
	movie.setAvailability(movieRequest.getAvailability());
	movie.setDescription(movieRequest.getDescription());
	movie.setSalePrice(movieRequest.getSalePrice());
	movie.setStock(movieRequest.getStock());
	movie.setTitle(movieRequest.getTitle());
	movie.setRentalPrice(movieRequest.getRentalPrice());
	
	return movie;
	}
	
	/**
	 * Crea lista de entidades de imagen de pelicula 
	 * @param movie - pelicula ingresada en BD
	 * @param movieRequest - JSON de entrada con link a imagen
	 * @return lista de entidad movieImage a ser escrita en BD
	 */
	public List<MovieImage> requestToMovieImage(Movie movie, MovieRequest movieRequest ) {
	
		List<MovieImage> images = new ArrayList<MovieImage>();
		List<MovieImageRequest> list = movieRequest.getImages();
		
		for (MovieImageRequest i : list) {
			
			images.add(new MovieImage(movie, i.getImage()));
			
		}
		
		return images;
		
	}
	
	/**
	 * Crea entidad que sera JSON de respuesta
	 * @param movieResponse - Pelicula ingresada a BD
	 * @param movieImage - Imagen de pelicula ingresada a BD
	 * @return entidad MovieRequest que sera JSON de salida
	 */
	public MovieRequest responseToMovie(Movie movieResponse, List<MovieImage> movieImage) {
		
		List<MovieImageRequest> images = new ArrayList<MovieImageRequest>();
		for (MovieImage mi : movieImage) {
			images.add(new MovieImageRequest(mi.getMoviePhoto()));
		}
		
		MovieRequest movieRequest = new MovieRequest();
		movieRequest.setTitle(movieResponse.getTitle());
		movieRequest.setAvailability(movieResponse.getAvailability());
		movieRequest.setDescription(movieResponse.getDescription());
		movieRequest.setImages(images);
		movieRequest.setMovieId(movieResponse.getMovieId());
		movieRequest.setRentalPrice(movieResponse.getRentalPrice());
		movieRequest.setSalePrice(movieResponse.getSalePrice());
		movieRequest.setStock(movieResponse.getStock());
		
		return movieRequest;
	}
	
	/**
	 * Prepara Movie existente para ser actualizada
	 * @param currentMovie Movie existente en BD
	 * @param request entidad de JSON de entrada
	 * @return Movie con datos actualizados para ingresar en BD
	 */
	public Movie prepareUpdate(Movie currentMovie, MovieRequest request) {
		
		Movie updateMovie = currentMovie;
		updateMovie.setAvailability(request.getAvailability());
		updateMovie.setDescription(request.getDescription());
		updateMovie.setRentalPrice(request.getRentalPrice());
		updateMovie.setSalePrice(request.getSalePrice());
		updateMovie.setStock(request.getStock());
		updateMovie.setTitle(request.getTitle());
		
		return updateMovie;
	}
	
	/**
	 * Prepara Movie para actualizar campo Avaliable
	 * @param currentMovie MOVIE que sera actualizada
	 * @param request campo de JSON de entrada
	 * @return MOVIE actualizada para ser escrita en BD
	 */
	public Movie prepareUpdateAval(Movie currentMovie, MovieRequest request) {
		
		Movie updateMovie = currentMovie;
		updateMovie.setAvailability(request.getAvailability());
		
		return updateMovie;
	}
	

}

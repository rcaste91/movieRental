package com.rcaste.movieRental.logic;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieLike;
import com.rcaste.movieRental.models.Rent;
import com.rcaste.movieRental.models.Users;
import com.rcaste.movieRental.models.requests.LikeMovieRequest;
import com.rcaste.movieRental.models.requests.RentRequest;
import com.rcaste.movieRental.repositories.ConstantsRepository;


public class MovieUserLogic {
	
	LikeMovieRequest response;

	public MovieUserLogic() {
		
		response = new LikeMovieRequest();
	}
	
	/**
	 * Prepara entidad a ser escrita en BD
	 * @param movie Movie existente
	 * @param user Usuario existente
	 * @return entidad MovieLike a ser escrita en BD
	 */
	public MovieLike prepareInsert(Movie movie, Users user) {
		
		MovieLike insert = new MovieLike();
		insert.setMovie(movie);
		insert.setUser(user);
		
		return insert;
		
	}
	
	/**
	 * Prepara respuesta JSON cuando se da un like
	 * @param like entidad MOVIELIKE escrita en BD
	 * @return entidad de respuesta JSON
	 */
	public LikeMovieRequest prepareLikeResponse (MovieLike like) {
		
		response = new LikeMovieRequest();
		
		response.setMovieId(like.getMovie().getMovieId().intValue());
		response.setUserId(like.getUser().getUserId().intValue());
		
		return response;
		
	}
	
	/**
	 * Prepara entidad Rent para escribir en BD
	 * @param request entidad de JSON de entrada
	 * @param movie MOVIE que sera rentada
	 * @param user USER que renta movie
	 * @return entidad RENT a escribir en BD
	 */
	public Rent prepareRentInsert (RentRequest request, Movie movie, Users user) {
		
		Rent rent = new Rent();
		float f =calcRentSubtotal(movie,request.getRentDate(),request.getDateReturn());
		
		if(f>0) {
			rent.setMovie(movie);
			rent.setUser(user);
			rent.setRentDate(request.getRentDate());
			rent.setReturnDate(request.getDateReturn());
			rent.setUserReturnDate(request.getActualReturn());
			rent.setTotal(request.getTotal());
			rent.setSubtotal(f);
		}
		
		
		return rent;
		
	}
	
	/**
	 * Prepara Entidad RENT para actualizar una MOVIE devuelta
	 * @param request JSON de entrada
	 * @param rentUpdate Entidad RENT que se actualiza
	 * @return entidad RENT ya lista para escribirse en BD
	 */
	public Rent prepareRentUpdate(RentRequest request, Rent rentUpdate, float delayValue) {
		
		Rent rent = rentUpdate;
		rent.setUserReturnDate(request.getActualReturn());
		
		LocalDateTime plannedReturnDate= rent.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime actualReturnDate= request.getActualReturn().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		long days=java.time.Duration.between(plannedReturnDate, actualReturnDate).toDays();
		
		if(days>0) {
			float delay= ( delayValue * days ) + rent.getSubtotal() ;
			rent.setTotal(delay);
		}else {
			rent.setTotal(rent.getSubtotal());
		}
		
		return rent;
	}
	
	/**
	 * prepara JSON de respuesta al crear una renta
	 * @param rent entidad RENT existente en BD
	 * @return entidad que sera devuelta
	 */
	public RentRequest prepareRentResponse(Rent rent) {
		
		RentRequest response = new RentRequest();
		
		response.setRentId(rent.getRentId().intValue());
		response.setActualReturn(rent.getUserReturnDate());
		response.setDateReturn(rent.getReturnDate());
		response.setMovieId(rent.getMovie().getMovieId().intValue());
		response.setUserId(rent.getUser().getUserId().intValue());
		response.setRentDate(rent.getRentDate());
		response.setSubtotal(rent.getSubtotal());
		response.setTotal(rent.getTotal());
		
		return response;
	}
	
	/**
	 * Calculacion de subtotal de renta si se entregara el dia establecido
	 * @param movie MOVIE que sera rentada
	 * @param rentDate fecha en la que se renta
	 * @param returnDate fecha en la que se debe devolver
	 * @return cantidad que costara la renta si no hay retrasos
	 */
	public float calcRentSubtotal(Movie movie, Date rentDate, Date returnDate) {
		
		float f = 0;
		float price=movie.getRentalPrice();
		
		LocalDateTime startDate=rentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime endDate=returnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		long days=java.time.Duration.between(startDate, endDate).toDays();
		
		if(days>0) {
			f= price * days;
		}
		
		
		return f;
	}
	
	
}

package com.rcaste.movieRental.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieLike;
import com.rcaste.movieRental.models.Rent;
import com.rcaste.movieRental.models.Sale;
import com.rcaste.movieRental.models.Users;
import com.rcaste.movieRental.models.requests.LikeMovieRequest;
import com.rcaste.movieRental.models.requests.RentRequest;
import com.rcaste.movieRental.models.requests.SaleRequest;


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
		Date rentDate = convertTimeZone(request.getRentDate());
		Date returnDate = convertTimeZone(request.getDateReturn());
		
		float f =calcRentSubtotal(movie, rentDate , returnDate);
		
		if(f>0) {
			rent.setMovie(movie);
			rent.setUser(user);
			rent.setRentDate(rentDate);
			rent.setReturnDate(returnDate);
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
		
		Date actualReturnDate= convertTimeZone(request.getActualReturn());
		
		Rent rent = rentUpdate;
		rent.setUserReturnDate(actualReturnDate);
		
		LocalDateTime plannedReturnDate= rent.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime actReturnDate= actualReturnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		long days=java.time.Duration.between(plannedReturnDate, actReturnDate).toDays();
		
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
	public RentRequest prepareRentCreateResponse(Rent rent) {
		
		RentRequest response = new RentRequest();
		
		response.setRentId(rent.getRentId().intValue());
		response.setDateReturn(rent.getReturnDate().toString());
		response.setMovieId(rent.getMovie().getMovieId().intValue());
		response.setUserId(rent.getUser().getUserId().intValue());
		response.setRentDate(rent.getRentDate().toString());
		response.setSubtotal(rent.getSubtotal());
		response.setTotal(rent.getTotal());

		response.setActualReturn("");
		
		return response;
	}
	
	/**
	 * prepara JSON de respuesta al actualizar una renta
	 * @param rent renta existente en la BD
	 * @return entidad JSON para mostrar actualizacion de renta
	 */
	public RentRequest prepareRentUpdateResponse(Rent rent) {
		
		RentRequest response = new RentRequest();
		
		response.setRentId(rent.getRentId().intValue());
		response.setDateReturn(rent.getReturnDate().toString());
		response.setMovieId(rent.getMovie().getMovieId().intValue());
		response.setUserId(rent.getUser().getUserId().intValue());
		response.setRentDate(rent.getRentDate().toString());
		response.setSubtotal(rent.getSubtotal());
		response.setTotal(rent.getTotal());
		response.setActualReturn(rent.getUserReturnDate().toString());
		
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
	
	/**
	 * prepara entidad SALE en base a JSON para ingreso a BD
	 * @param request JSON de entrada
	 * @param movie MOVIE existente que se vendera
	 * @param user USER que compra 
	 * @param price precio de pelicula
	 * @return
	 */
	public Sale prepareSaleInsert(SaleRequest request, Movie movie, Users user, float price) {
		
		Sale sale = new Sale();
		
		sale.setMovie(movie);
		sale.setUser(user);
		sale.setQuantity(request.getQuantity());
		sale.setTotal(request.getQuantity() * price);
		sale.setSaleDate( convertTimeZone(request.getSaleDate()) );
		
		
		return sale;
	}
	
	/**
	 * prepara JSON de respuesta al guardar una venta
	 * @param sale SALE existente en BD
	 * @return JSON de respuesta
	 */
	public SaleRequest prepareSaleResponse(Sale sale) {
		
		SaleRequest response = new SaleRequest();
		
		response.setMovieId(sale.getMovie().getMovieId().intValue());
		response.setQuantity(sale.getQuantity());
		response.setSaleDate( sale.getSaleDate().toString());
		response.setSaleId(sale.getSaleId().intValue());
		response.setTotal(sale.getTotal());
		response.setUserId(sale.getUser().getUserId().intValue());
		
		return response;
		
	}
	
	/**
	 * Conversion de fecha de JSON a hora local -06:00
	 * @param dateString fecha de entrada en JSON
	 * @return Date en hora local
	 */
	private Date convertTimeZone(String dateString) {
		
		
		try {
			
			SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			isoFormat.setTimeZone(TimeZone.getTimeZone("GMT-6"));
			Date date = isoFormat.parse(dateString);
			return date;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
		
	}
	
	
}

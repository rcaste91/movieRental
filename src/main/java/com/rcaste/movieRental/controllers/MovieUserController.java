package com.rcaste.movieRental.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rcaste.movieRental.logic.MovieUserLogic;
import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieLike;
import com.rcaste.movieRental.models.Rent;
import com.rcaste.movieRental.models.Sale;
import com.rcaste.movieRental.models.Users;
import com.rcaste.movieRental.models.requests.RentRequest;
import com.rcaste.movieRental.models.requests.SaleRequest;
import com.rcaste.movieRental.models.requests.LikeMovieRequest;
import com.rcaste.movieRental.repositories.ConstantsRepository;
import com.rcaste.movieRental.repositories.MovieLikeRepository;
import com.rcaste.movieRental.repositories.MovieRepository;
import com.rcaste.movieRental.repositories.RentRepository;
import com.rcaste.movieRental.repositories.SaleRepository;
import com.rcaste.movieRental.repositories.UsersRepository;

@RestController
@RequestMapping("api/v1/")
public class MovieUserController {
	
	@Autowired
	private MovieRepository mRepository;
	
	@Autowired
	private UsersRepository uRepository;
	
	@Autowired
	private MovieLikeRepository mlRepository;
	
	@Autowired
	private RentRepository rRepository;
	
	@Autowired
	private ConstantsRepository cRepository;
	
	@Autowired
	private SaleRepository sRepository;
	
	private MovieUserLogic logic;
	
	public MovieUserController() {
		// TODO Auto-generated constructor stub
		logic=new MovieUserLogic();
	}
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="movies/like", method=RequestMethod.POST)
	public LikeMovieRequest likeMovie(@RequestBody LikeMovieRequest movieLike) {
		
		LikeMovieRequest response = new LikeMovieRequest();
		
		Optional<Movie> movie = mRepository.findById((long) movieLike.getMovieId());
		Optional<Users> user = uRepository.findById((long) movieLike.getUserId());
		
		if((!movie.isEmpty()) && (!user.isEmpty())) {
			MovieLike like=mlRepository.saveAndFlush(logic.prepareInsert(movie.get(), user.get()));
			response=logic.prepareLikeResponse(like);
		}
		
		return response;
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="movies/rent", method=RequestMethod.POST)
	public RentRequest rentMovie (@RequestBody RentRequest movieRent) {
		
		RentRequest response = new RentRequest();
		
		Optional<Movie> movie=mRepository.findById((long) movieRent.getMovieId());
		Optional<Users> user = uRepository.findById((long) movieRent.getUserId());
		
		
		if( (!movie.isEmpty()) && (!user.isEmpty()) ) {
			
			int stock=movie.get().getStock();
			
			if(stock >=1) {
				Rent prepareRent =logic.prepareRentInsert(movieRent, movie.get(), user.get());
				
				if(prepareRent.getSubtotal()>0) {
					Rent rent= rRepository.saveAndFlush(prepareRent);
					updateMovieStock(movie.get(), 1);
					response=logic.prepareRentCreateResponse(rent);
				}
			}
			
		}

		return response;
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="movies/rent", method=RequestMethod.PUT)
	public RentRequest returnRentMovie(@RequestBody RentRequest movieRent) {
		
		RentRequest response = new RentRequest();
		float delay=cRepository.findDelay("delay");
		
		Optional<Rent> rentFind = rRepository.findById((long) movieRent.getRentId());
		
		if(!rentFind.isEmpty()) {
			
			Rent rentUpdate = rRepository.saveAndFlush(logic.prepareRentUpdate(movieRent, rentFind.get(),delay));
			updateMovieStock(rentFind.get().getMovie(), -1);
			response=logic.prepareRentUpdateResponse(rentUpdate);
		}
		
		return response;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="movies/sell", method=RequestMethod.POST)
	public SaleRequest sellMovie(@RequestBody SaleRequest request) {
		
		SaleRequest response = new SaleRequest();
		
		Optional<Movie> movieFind = mRepository.findById((long) request.getMovieId());
		Optional<Users> userFind = uRepository.findById((long) request.getUserId());
		
		if( (!movieFind.isEmpty()) && (!userFind.isEmpty()) ) {
			
			int stock=movieFind.get().getStock();
			
			if(request.getQuantity()<=stock) {
				
				Sale insertSale=logic.prepareSaleInsert(request, movieFind.get(), userFind.get(), movieFind.get().getSalePrice());
				Sale movieSold = sRepository.saveAndFlush(insertSale);
				updateMovieStock(movieFind.get(), request.getQuantity());
				response = logic.prepareSaleResponse(movieSold);
			}
			
			
		}
		
		return response;
	}
	
	private void updateMovieStock(Movie m, int quantity) {
		
		m.setStock( m.getStock()-quantity );
		mRepository.saveAndFlush(m);
	}

}

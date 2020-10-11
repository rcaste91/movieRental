package com.rcaste.movieRental.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rcaste.movieRental.models.Movie;
import com.rcaste.movieRental.models.MovieLike;
import com.rcaste.movieRental.models.Rent;
import com.rcaste.movieRental.models.Sale;
import com.rcaste.movieRental.models.Users;
import com.rcaste.movieRental.models.requests.LikeMovieRequest;
import com.rcaste.movieRental.models.requests.RentRequest;
import com.rcaste.movieRental.models.requests.SaleRequest;

@SpringBootTest
public class UserLogicTests {

	private MovieUserLogic logic;
	private Movie mo;
	private Users us;
	
	public UserLogicTests() {
		// TODO Auto-generated constructor stub
		logic=new MovieUserLogic();
		 mo = createTestMovie();
		 us = createTestUser();
	}
	
	@Test
	void testMovieLikePrepartInsert() {
		
		Movie m = createTestMovie();
		Users u = createTestUser();
		MovieLike ml =logic.prepareInsert(m,u);
		
		assertTrue(ml.getMovie().getMovieId() == m.getMovieId());
		
	}
	
	@Test
	void testMovieLikePrepareResponse() {
		
		Movie m = createTestMovie();
		Users u = createTestUser();
		MovieLike ml = new MovieLike();
		ml.setLikeId((long) 1);
		ml.setMovie(m);
		ml.setUser(u);
		
		LikeMovieRequest lmr=logic.prepareLikeResponse(ml);
		
		assertTrue(lmr.getMovieId() == ml.getMovie().getMovieId().intValue());
	}
	
	@Test
	void testRentPrepareInsert() {
		
		RentRequest re = new RentRequest();
		re.setUserId(us.getUserId().intValue());
		re.setMovieId(mo.getMovieId().intValue());
		re.setActualReturn("2020-11-05T18:25");
		re.setDateReturn("2020-11-05T18:25");
		re.setRentDate("2020-10-31T18:25");
		
		Rent r=logic.prepareRentInsert(re, mo, us);
		
		assertTrue(r.getMovie().getMovieId() == re.getMovieId());
	}
	
	@Test
	void testCalcRentSubtotal() {
		
		RentRequest re = new RentRequest();
		re.setUserId(us.getUserId().intValue());
		re.setMovieId(mo.getMovieId().intValue());
		re.setActualReturn("2020-11-05T18:25");
		re.setDateReturn("2020-11-05T18:25");
		re.setRentDate("2020-10-31T18:25");
		
		Rent r=logic.prepareRentInsert(re, mo, us);
		
		float sub=logic.calcRentSubtotal(mo, r.getRentDate(), r.getReturnDate());
		
		assertTrue(sub>0);
		
	}
	
	
	
	@Test
	void testCovertSaleRequestToSale() {
		
		SaleRequest s = new SaleRequest();
		
		s.setMovieId(1);
		s.setQuantity(2);
		s.setSaleId(1);
		s.setTotal((float) 5.4);
		s.setSaleDate("2020-10-11T05:00");
		s.setUserId(1);
		
		
		Sale saleSaved= new Sale();
		Movie movie = createTestMovie();
		Users user = createTestUser();
		
		saleSaved=logic.prepareSaleInsert(s,movie,user, (float) 2.5);
		float totalExpected = 5;
		
		assertTrue( saleSaved.getTotal() == totalExpected );
	}
	
	@Test
	void testConvertSaletoSaleRequest() {
		
		SaleRequest s = new SaleRequest();
		
		Movie movie = createTestMovie();
		Users user = createTestUser();
		Date today = new Date();
		float total = 5;
		
		Sale sale = new Sale();
		sale.setMovie(movie);
		sale.setQuantity(2);
		sale.setSaleId((long) 2);
		sale.setSaleDate(today);
		sale.setTotal(total);
		sale.setUser(user);
		
		s=logic.prepareSaleResponse(sale);
		
		assertTrue( s.getSaleId()==sale.getSaleId().intValue() );
		
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
	
	Users createTestUser() {
		
		Users u = new Users();
		u.setEmail("email@gmail.com");
		u.setName("test");
		u.setPassword("test");
		u.setUserId((long) 1);
		u.setUsername("test");
		
		return u;
	}
	
}

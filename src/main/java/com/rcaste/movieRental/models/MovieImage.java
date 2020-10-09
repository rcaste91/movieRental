package com.rcaste.movieRental.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "movie_image")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MovieImage {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
	private Long imageId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="movie_id")
	private Movie movie;
		

    @Column(name = "movie_photo")
    private String moviePhoto;
	
	public MovieImage() {
		
	}
	
	public MovieImage(Movie movie, String moviePhoto) {
		this.movie=movie;
		this.moviePhoto=moviePhoto;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getMoviePhoto() {
		return moviePhoto;
	}

	public void setMoviePhoto(String moviePhoto) {
		this.moviePhoto = moviePhoto;
	}
	
	

}

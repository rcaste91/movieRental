package com.rcaste.movieRental.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
		
	@Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "movie_photo")
    private byte[] moviePhoto;
	
	public MovieImage() {
		
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

	public byte[] getMoviePhoto() {
		return moviePhoto;
	}

	public void setMoviePhoto(byte[] moviePhoto) {
		this.moviePhoto = moviePhoto;
	}
	
	

}

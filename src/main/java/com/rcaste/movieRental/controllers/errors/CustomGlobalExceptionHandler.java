package com.rcaste.movieRental.controllers.errors;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/*
	@ExceptionHandler(MovieNotFoundException.class)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "Movie not found");
    }
    */
	
	@ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage("Movie not found");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
	
	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customUserNotFound(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage("User not found");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
	
	@ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customImageNotFound(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage("Image not found");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
	
	@ExceptionHandler(RentNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customRentNotFound(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage("Rent not found");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
	
}

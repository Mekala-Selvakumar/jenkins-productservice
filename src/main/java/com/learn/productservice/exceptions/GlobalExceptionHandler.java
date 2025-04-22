package com.learn.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<?> productNotFoundExceptionHandler(ProductNotFoundException exp) {

		return new ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST);

	}

	// other exceptions
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception exp) {
		ResponseEntity<?> entity = new ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST);
		return entity;
	}

}

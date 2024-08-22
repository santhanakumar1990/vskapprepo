package com.vsk.product.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

import lombok.Getter;

@Getter
public class InvalidInputException extends Exception {

	private List<ObjectError> errors;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(String message,List<ObjectError> errors) {
		super(message);
		this.errors = errors;
	}

}

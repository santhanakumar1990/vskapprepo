package com.vsk.product.controler.advisor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vsk.product.dto.Response;
import com.vsk.product.exception.InvalidInputException;
import com.vsk.product.exception.ResourceAlreadyExistsException;
import com.vsk.product.exception.ResourceNotFountException;
import com.vsk.product.exception.SQLOperationException;
import com.vsk.product.exception.ServiceFailureException;

@ControllerAdvice
public class ControllerAdvisor {

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<Response<List<String>>> handleInvalidInput(InvalidInputException ex) {

		Response<List<String>> response = null;

		if (ex.getErrors() != null) {
			response = new Response.builder<List<String>>().message(ex.getMessage()).status(false)
					.errors(getErrorMessages(ex.getErrors())).build();

		} else {
			response = new Response.builder<List<String>>().message(ex.getMessage()).status(false).result(null).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(ResourceNotFountException.class)
	public ResponseEntity<Response<Void>> handleResourceNotFoundException(ResourceNotFountException ex) {

		Response<Void> response = new Response.builder<Void>().message(ex.getMessage()).status(false).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

	}

	@ExceptionHandler(SQLOperationException.class)
	public ResponseEntity<Response<Void>> handleSqlOperation(SQLOperationException ex) {

		Response<Void> response = new Response.builder<Void>().message(ex.getMessage()).status(false).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

	}
	
	@ExceptionHandler(ServiceFailureException.class)
	public ResponseEntity<Response<Void>> handleServiceFailure(ServiceFailureException ex) {

		Response<Void> response = new Response.builder<Void>().message(ex.getMessage()).status(false).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

	}

	public List<String> getErrorMessages(List<ObjectError> errors) {
		List<String> errMessages = errors.stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
		return errMessages;
	}

}

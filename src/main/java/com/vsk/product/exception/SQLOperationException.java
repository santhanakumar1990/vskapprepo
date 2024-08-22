package com.vsk.product.exception;

import java.sql.SQLException;

public class SQLOperationException extends SQLException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SQLOperationException(String message) {
		super(message);
	}

}

package org.sid.ebankingbackend.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends RuntimeException{

	public ApiBaseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public abstract HttpStatus getStatusCode();
	
}

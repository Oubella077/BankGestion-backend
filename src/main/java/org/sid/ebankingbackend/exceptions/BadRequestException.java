package org.sid.ebankingbackend.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiBaseException {

	public BadRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public HttpStatus getStatusCode() {
		// TODO Auto-generated method stub
		return HttpStatus.BAD_REQUEST;
	}
	
	

}

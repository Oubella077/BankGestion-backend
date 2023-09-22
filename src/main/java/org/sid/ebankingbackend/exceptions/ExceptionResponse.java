package org.sid.ebankingbackend.exceptions;

public class ExceptionResponse<T> {
	
	private boolean ok;
	
	private String message;

	public ExceptionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionResponse(boolean ok, String message) {
		super();
		this.ok = ok;
		this.message = message;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

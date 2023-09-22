package org.sid.ebankingbackend.exceptions;

import java.util.Date;

public class ExceptionDetails {

	private String message;
	private String url;
	private Date timestap;
	public ExceptionDetails() {
		super();
		// TODO Auto-generated constructor stub
		this.timestap = new Date();
	}

	public ExceptionDetails(String message, String url) {
		super();
		this.message = message;
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getTimestap() {
		return timestap;
	}

	public void setTimestap(Date timestap) {
		this.timestap = timestap;
	}

	
	
}

package com.agilejerry.springmaster;

import org.springframework.stereotype.Repository;

@Repository
public class EventDao {
	private String message = "Hello";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}

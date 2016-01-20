package com.agilejerry.springmaster;

import org.springframework.stereotype.Repository;

@Repository
public class Greeter {
	private String name;
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String sayHello() {
        return "Hello Spring!";
    }
}

package com.agilejerry.springmaster;

public class Customer {
	private Greeter greeter;
	private String name;
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Greeter getGreeter() {
		return greeter;
	}
	public void setGreeter(Greeter greeter) {
		this.greeter = greeter;
	}
}

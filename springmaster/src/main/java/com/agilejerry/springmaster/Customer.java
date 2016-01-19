package com.agilejerry.springmaster;

import org.springframework.beans.factory.annotation.Autowired;

public class Customer {
	private Greeter greeter;
	private String name;
	
	@Autowired
	private Wallet wallet;
	
	private Menu menu;
	
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
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
}

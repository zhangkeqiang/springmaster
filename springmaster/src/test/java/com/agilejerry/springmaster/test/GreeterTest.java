package com.agilejerry.springmaster.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.Customer;
import com.agilejerry.springmaster.Greeter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:customerTest.xml") 
public class GreeterTest {
	
	 
	private Customer customerA;
	
	@Autowired 
	private Greeter greeterA;
	

	
	@Test 
	public void autowired_class_should_be_instantiated(){
		//Assert.assertNotNull(customerA);
		Assert.assertNotNull(greeterA);
	}
}

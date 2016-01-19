package com.agilejerry.springmaster;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Greeter.class)
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

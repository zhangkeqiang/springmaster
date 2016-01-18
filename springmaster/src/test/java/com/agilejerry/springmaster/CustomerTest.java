package com.agilejerry.springmaster;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerTest {
	@Test
	public void testInit(){
		ApplicationContext context = new ClassPathXmlApplicationContext(   
                "applicationContext.xml"); 
		Greeter greeter = (Greeter) context.getBean("Greeter");
		Customer customer = (Customer) context.getBean("Customer");
		Assert.assertNotNull(greeter);
		Assert.assertNotNull(customer.getGreeter());
		Assert.assertEquals("Mike", greeter.getName());
		Assert.assertEquals("Jerry", customer.getName());
		Assert.assertEquals(greeter, customer.getGreeter());
	}
}

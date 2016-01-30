package com.agilejerry.springmaster.test;


import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.Event;
import com.agilejerry.springmaster.Menu;
import com.agilejerry.springmaster.impl.MiddleGradeMenu;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class EventTest {
	
	@Autowired 
	private Event event;
	

	@Autowired
	@Qualifier("HighGradeMenu")
	private Menu highGradeMenu;
	
	@Autowired
	@Qualifier("MiddleGradeMenu")
	private Menu middleGradeMenu;
	@Test 
	public void autowired_class_should_be_instantiated(){
		Assert.assertNotNull(event);
		Assert.assertNotNull(event.getWallet());
	}
	
	@Test
	public void highGradeMenu_is_High_Grade(){
		Assert.assertEquals("High Grade", highGradeMenu.getGrade());
	}

	@Test
	public void middleGradeMenu_is_Middle_Grade(){
		Assert.assertEquals("Middle Grade", middleGradeMenu.getGrade());
	}
	


}

package com.agilejerry.springmaster.dailytest;


import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.dao.UserDaoOld;
import com.agilejerry.springmaster.dao.UserDao;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class UserDaoTest {
	private static final Logger log = LogManager.getLogger(OldUserDaoTest.class);
	@Rule 
	public TestName testName = new TestName();
	
	@Before
	public void setUp(){
		log.warn(testName.getMethodName() + " Start...");
	}
	
	@After
	public void tearDown(){
		log.warn(testName.getMethodName() + " end");
		//dao.closeSession();
	}

	@Test
	public void test(){
	    
	}
	
	

}

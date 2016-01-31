package com.agilejerry.springmaster.test;


import static org.junit.Assert.*;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.dao.GroupDaoOld;
import com.agilejerry.springmaster.dao.UserDaoOld;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class ExampleOfTest {
	private static final Logger log = LoggerFactory.getLogger(ExampleOfTest.class);
	
	@Rule 
	public TestName testName = new TestName();
	@Before
	public void setUp(){
		log.warn(testName.getMethodName() + " Start...");		
	}
	@After
	public void tearDown(){
		log.warn(testName.getMethodName() + " end");
	}
	@Test
	public void test1() {
		
	
	}
	
	@Test
	public void test2() {
		log.warn("log===============");
	}

	

	

}

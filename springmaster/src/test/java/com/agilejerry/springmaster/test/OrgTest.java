package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class OrgTest {

	//@Resource
	private SessionFactory sessionFactory;
	
	@Test
	public void test() {
		//Serializable id = sessionFactory.getCurrentSession().save(new OrgBean("微软公司"));
	}

}

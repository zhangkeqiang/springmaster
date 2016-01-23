package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.entity.OrgBean;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class OrgTest {

	@Resource
	private SessionFactory sessionFactory;
	
	@Before
	public void setUp(){
		;
	}
	@Test
	public void test() {
		Serializable id = getSession().save(new OrgBean("微软公司"));
	}
	private Session getSession() {
		return sessionFactory.openSession();
	}

}

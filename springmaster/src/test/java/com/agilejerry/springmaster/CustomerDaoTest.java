package com.agilejerry.springmaster;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.agilejerry.springmaster.entity.CustomerBean;
import com.agilejerry.springmaster.entity.RelativeBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class CustomerDaoTest {
	
	@Resource
	private SessionFactory sessionFactory;
	Session session ;
	
	@Before
	public void setUp() {
		 session = sessionFactory.openSession();

	}

	@Test
	public void testCRUDOfCustomer() {		
		CustomerBean customer = new CustomerBean("李琴箫", "password231");
		
		session.beginTransaction();
		Serializable userId = session.save(customer);
		session.getTransaction().commit();
		Assert.assertEquals(customer.getId() > 1, true);
		customer.setUsername("张海阳");
		session.beginTransaction();
		session.update(customer);
		session.getTransaction().commit();
		
		CustomerBean userB = (CustomerBean) session.get(CustomerBean.class, userId);
		System.out.println(userB.getUsername());
		session.beginTransaction();
		session.delete(userB);
		session.getTransaction().commit();
		CustomerBean userC = (CustomerBean) session.get(CustomerBean.class, userId);
		Assert.assertNull(userC);
	}

	@Test
	public void addOneToManyRelative(){
		CustomerBean customer = new CustomerBean("李琴箫", "password231");
		
	}
	
	
	@Test
	public void test_add_new_customer_and_relative() {
		System.out.println("Hibernate one to many Annotation");
		CustomerBean customA = new CustomerBean("李莲英", "122643337");
		RelativeBean relativeA = new RelativeBean("李海燕", "13823330545");
		try {
			session.beginTransaction();
			session.save(customA);
			relativeA.setCustomerBean(customA);
			RelativeBean relativeB = new RelativeBean(customA, "张思德", "13823333333");
			session.save(relativeA);
			session.save(relativeB);
			Set<RelativeBean> relativeBeans = new HashSet<RelativeBean>();
			relativeBeans.add(relativeA);
			relativeBeans.add(relativeB);
			customA.setRelativeBeans(relativeBeans);
			session.getTransaction().commit();
			Assert.assertEquals(true, relativeA.getId().intValue() > 0);
		} catch (HibernateException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}


		CustomerBean customB = (CustomerBean) session.get(CustomerBean.class, customA.getId());
		Assert.assertEquals(2, customB.getRelativeBeans().size());



		session.beginTransaction();
		session.delete(customA);
		session.getTransaction().commit();
	}
	
	@Test
	public void test_read_relative() {
		System.out.println("test_read_relative");
		//session.beginTransaction();
		CustomerBean customA = (CustomerBean) session.byId(CustomerBean.class).getReference(1);
		Set<RelativeBean> relativeBeans = customA.getRelativeBeans();
		Assert.assertEquals(2, relativeBeans.size());
		for (RelativeBean relativeBean : relativeBeans) {
			System.out.println(relativeBean.getName() + relativeBean.getPhone());
		}
		//session.getTransaction().commit();
	}
	
	@After
	public void clear(){
		session.close();
	}

}

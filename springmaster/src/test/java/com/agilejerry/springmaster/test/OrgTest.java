package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class OrgTest {

	@Resource
	private SessionFactory sessionFactory;
	private Session ss;
	@Before
	public void setUp(){
		ss = sessionFactory.openSession();
	}
	@Test
	public void test() {
		Serializable id = ss.save(new OrgBean("微软公司BBB"));
		System.out.println("Save result Serializable is " + id);
		OrgBean org = (OrgBean) ss.get(OrgBean.class, id);
		System.out.println("Org id is " + org.getId());
		ss.beginTransaction();
		ss.delete(org);
		ss.getTransaction().commit();
	}
	
	@Test
	public void testlist() {
		@SuppressWarnings("unchecked")
		List<OrgBean> orgList = ss.createQuery("FROM OrgBean").list();
		for(OrgBean org:orgList){
			System.out.println(org);
			Set<UserBean> users = org.getUsers();
			for(UserBean user: users){
				System.out.println(user);
			}
		}
	}

	@After
	public void tearDown(){
		ss.close();
	}
}

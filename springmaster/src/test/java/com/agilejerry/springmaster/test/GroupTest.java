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

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class GroupTest {

	@Resource
	private SessionFactory sessionFactory;
	private Session ss;
	@Before
	public void setUp(){
		ss = sessionFactory.openSession();
	}
	@Test
	public void testGroupCRUD() {
		GroupBean group1 = new GroupBean();
		group1.setName("生产队");
		group1.setType("Administration");
		Serializable id = ss.save(group1);
		System.out.println("Save result Serializable is " + id);
		System.out.println(group1);
		GroupBean group2 = (GroupBean) ss.get(GroupBean.class, id);
		System.out.println(group2);
		ss.beginTransaction();
		//ss.delete();
		ss.getTransaction().commit();
	}
	
	@Test
	public void testlist() {
		@SuppressWarnings("unchecked")
		List<GroupBean> groupList = ss.createQuery("FROM GroupBean").list();
		for(GroupBean group:groupList){
			System.out.println(group);
			Set<UserBean> users = group.getUsers();
			for(UserBean user: users){
				System.out.println(user);
				System.out.println(user.getOrg());
			}
		}
	}

	@After
	public void tearDown(){
		ss.close();
	}
}

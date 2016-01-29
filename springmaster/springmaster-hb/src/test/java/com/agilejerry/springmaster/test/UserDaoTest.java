package com.agilejerry.springmaster.test;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.dao.UserDao;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class UserDaoTest {
	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	@Rule 
	public TestName testName = new TestName();
	@Autowired
	private UserDao dao;
	
	@Before
	public void setUp(){
		log.warn(testName.getMethodName() + " Start...");		
	}
	
	@After
	public void tearDown(){
		log.warn(testName.getMethodName() + " end");
	}
	@Test
	public void test() {
		UserBean user =new UserBean();
		user.setUserName("李珊珊");
		Assert.assertTrue(dao.create(user) > 0);
		log.warn(user.toString());
		Assert.assertTrue(dao.delete(user));
	}
	
	
	@Test
	public void list_show_all_user() {
		List<UserBean> list = dao.list();
		
		for(UserBean user:list){
			log.warn(user.toString());
			log.warn(user.getOrg().toString());
			Set<GroupBean> groups = user.getGroups();
			for(GroupBean group:groups){
				log.warn(group.toString());
			}
		}
	}
	
	

}

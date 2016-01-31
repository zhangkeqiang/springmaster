package com.agilejerry.springmaster.test;


import static org.junit.Assert.*;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.ArrayList;
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

import com.agilejerry.springmaster.StateCode;
import com.agilejerry.springmaster.dao.GroupDao;

import com.agilejerry.springmaster.dao.OrgDao;
import com.agilejerry.springmaster.dao.UserDao;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.GroupMemberBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;

import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class GroupMemberTest {
	private static final Logger LOGGER = LogManager.getLogger(GroupMemberTest.class);
	
   @Resource
    private SessionFactory sessionFactory;
	   
	@Autowired 
	private GroupDao groupDao;
	@Autowired
	private UserDao userDao;
    @Autowired
    private OrgDao orgDao;    
	@Rule 
	public TestName testName = new TestName();
	@Before
	public void setUp(){
		LOGGER.warn(testName.getMethodName() + " Start...");
		Assert.assertNotNull(groupDao);
	}	
	@After
	public void tearDown(){
		LOGGER.warn(testName.getMethodName() + " end");
		groupDao.closeSession();
	}
	
	@Test
	public void listGroupMember(){
	    Session ss = groupDao.getSession();
	    String hql = "FROM GroupMemberBean";
	    List<GroupMemberBean> list = ss.createQuery(hql).list();
	    for(GroupMemberBean member:list){
	        LOGGER.info(member);
	    }
	}
}

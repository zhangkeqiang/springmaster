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
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.dao.GroupDao;
import com.agilejerry.springmaster.dao.OrgDao;
import com.agilejerry.springmaster.dao.UserDao;
import com.agilejerry.springmaster.dao.UserDao2;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class OrgDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(OrgDaoTest.class);
	@Rule 
	public TestName testName = new TestName();
	
	@Autowired
	private OrgDao dao;
	@Autowired
    private UserDao userDao;
	@Before
	public void setUp(){
		LOGGER.warn(testName.getMethodName() + " Start...");		
	}
	
	@After
	public void tearDown(){
		LOGGER.warn(testName.getMethodName() + " end");
		dao.closeSession();
	}
	
   @Test
    public void init_test(){
        Assert.assertNotNull(dao);
    }
   
	@Test
	public void test_list_org_and_member(){
	    List<OrgBean> list = dao.list();
	    for(OrgBean org:list){
	        LOGGER.info(org); 
	        Set<UserBean> users = org.getUsers();
	        for(UserBean user:users){
	            LOGGER.info(user);
	        }
	    }
	}
	
	@Test 
	public void test_change_org(){
	    OrgBean org = dao.get(1);
	    userDao.setSession(dao.getSession());
	    UserBean userA = userDao.get(4);
	    OrgBean userOldOrg = userA.getOrg();
	    userA.setOrg(org);
	    
	    dao.flushSession();
	    UserBean userB = userDao.get(4);
	    Assert.assertEquals(org.getName(), userB.getOrg().getName());	    
	    userB.setOrg(userOldOrg);
	    userDao.update(userB);
	}
	
	
}

	


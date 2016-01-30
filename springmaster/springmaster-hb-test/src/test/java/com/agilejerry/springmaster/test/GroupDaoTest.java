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

import com.agilejerry.springmaster.dao.GroupDao;
import com.agilejerry.springmaster.dao.UserDao;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;

import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class GroupDaoTest {
	private static final Logger log = LogManager.getLogger(GroupDaoTest.class);
	
   @Resource
    private SessionFactory sessionFactory;
	   
	@Autowired 
	private GroupDao groupDao;
	@Autowired
	private UserDao userDao;	
	@Rule 
	public TestName testName = new TestName();
	@Before
	public void setUp(){
		log.warn(testName.getMethodName() + " Start...");
		Assert.assertNotNull(groupDao);
	}
	@Test
	public void testGroupCRUD() {
		
		GroupBean groupA = new GroupBean();
		groupA.setName("销售2组");
		groupA.setType("Administration");
		int aGroupID = groupDao.create(groupA);
		Assert.assertTrue(aGroupID>1);
		groupA.setType("Administration");
		groupA.setName("直销2组");
		groupDao.update(groupA);
		GroupBean groupB = groupDao.get(aGroupID);
		Assert.assertEquals(groupA.getName(), groupB.getName());
		groupDao.delete(groupB);
		GroupBean groupC = groupDao.get(aGroupID);
		Assert.assertNull(groupC);
	}
	

    int[][] data = {
//            {50, 1, GroupDao.HAVE_ADMINISTRATION_GROUP},
//            {1,  1, GroupDao.DUPLICATED_MEMBER},
//            {50, 3, GroupDao.HAVE_ADMINISTRATION_GROUP},
//            {50, 4, GroupDao.HAVE_ADMINISTRATION_GROUP},
            {1, 2, GroupDao.OK}
    };
    
	@Test 
	public void user_should_belong_only_one_administration_group1(){
		for(int i=0;i<data.length;i++){
			GroupBean groupA = groupDao.get((int)data[i][0]);
			userDao.setSession(groupDao.getSession());
			UserBean userA = userDao.get((int)data[i][1]);
			log.info(groupA);
			log.info(userA);
			Assert.assertEquals((int)data[i][2], groupDao.addMember(groupA, userA));
			if(data[i][2]!= GroupDao.HAVE_ADMINISTRATION_GROUP)
			    Assert.assertTrue(userDao.isJoined(userA, groupA));
		}
		
//        for(int i=0;i<data.length;i++){
//            if(data[i][2] == GroupDao.OK){
//                // new group member need be removed
//              GroupBean group = groupDao.get(data[i][0]);
//              UserBean user = userDao.get(data[i][1]);
//              groupDao.removeMember(group, user);
//            }
//        }
	}
	
	@Test
	public void group_users_can_be_get(){
		log.warn(testName.getMethodName());
//		GroupBean groupA = groupDao.get(50);
//		Set<UserBean> users = groupDao.getUsers(groupA);
		
		Session s = sessionFactory.openSession();
        
        GroupBean g = (GroupBean) s.get(GroupBean.class, 50);
        Set<UserBean> users = g.getUsers();
       
        
		for(UserBean user: users){
			log.warn(user.toString());
			log.warn(user.getOrg().toString());
		}
		
	    s.close();
	}
	
	

	

	
	@After
	public void tearDown(){
		log.warn(testName.getMethodName() + " end");
	      //clear the changed or added data
/*        for(int i=0;i<data.length;i++){
            if(data[i][2] == GroupDao.OK){
                // new group member need be removed
              GroupBean group = groupDao.get(data[i][0]);
              UserBean user = userDao.get(data[i][1]);
              userDao.breakAwayGroup(user, group);
            }
        }*/
		groupDao.closeSession();
	}
}
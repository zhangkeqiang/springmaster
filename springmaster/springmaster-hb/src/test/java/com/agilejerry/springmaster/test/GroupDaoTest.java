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
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;

import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class GroupDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(GroupDaoTest.class);
	
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
	@Test
	public void testGroupCRUD() {
		
		GroupBean groupA = new GroupBean();
		groupA.setName("销售2组");
		groupA.setType("Administration");
		groupA.setOrg(orgDao.getDefault());
		groupDao.setSession(orgDao.getSession());
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
//            {6, 1, StateCode.DUPLICATED_MEMBER,-1},
//            {1,  1, StateCode.DUPLICATED_MEMBER,-2},
//            {50, 3, StateCode.DUPLICATED_MEMBER,-1},
//            {50, 4, StateCode.DUPLICATED_MEMBER,-1},
//            {1, 2, StateCode.OK,1},
            {1, 3, StateCode.OK,1}
    };
    
    @Test
    public void testisJoined(){
        userDao.setSession(groupDao.getSession());
        for(int i=0;i<data.length;i++){
            GroupBean groupA = groupDao.get((int)data[i][0]);            
            UserBean userA = userDao.get((int)data[i][1]);
            Assert.assertEquals(data[i][2] == StateCode.DUPLICATED_MEMBER, userDao.checkUserJoinGroup(userA, groupA));
        }
    }
	@Test 
	public void user_can_be_joined_into_one_group(){
		for(int i=0;i<data.length;i++){
			GroupBean groupA = groupDao.get((int)data[i][0]);
			userDao.setSession(groupDao.getSession());
			UserBean userA = userDao.get((int)data[i][1]);
			LOGGER.info(groupA);
			LOGGER.info(userA);
			Assert.assertEquals((int)data[i][3], groupDao.addMember(groupA, userA));
			
			if(data[i][3]!= StateCode.HAVE_ADMINISTRATION_GROUP){
			    Set<UserBean> members = groupA.getUsers();
			    for(UserBean member: members){
			        LOGGER.info(member);
			    }
			    UserBean userB = userDao.get(userA.getUserNo());
			    Set<GroupBean> groupList = (Set<GroupBean>) userB.getGroups();
		        
		        for(GroupBean userGroup:groupList){
		            LOGGER.warn(userGroup);
		        }        
			    Assert.assertTrue(userDao.checkUserJoinGroup(userB, groupA));
			}
		}
		
        for(int i=0;i<data.length;i++){
            if(data[i][3] == StateCode.OK){
              // new group member need be removed
              GroupBean group = groupDao.get(data[i][0]);
              UserBean user = userDao.get(data[i][1]);
              groupDao.removeMember(group, user);
              Set<UserBean> members = group.getUsers();
              for(UserBean member: members){
                  LOGGER.info(member);
              }
              UserBean userC = userDao.get(data[i][1]);
              Set<GroupBean> groupList = (Set<GroupBean>) userC.getGroups();              
              for(GroupBean userGroup:groupList){
                  LOGGER.warn(userGroup);
              }    
              Assert.assertFalse(userDao.checkUserJoinGroup(userC, group));
            }
        }
	}
	
	@Test
	public void group_users_can_be_get(){
		LOGGER.warn(testName.getMethodName());
//		GroupBean groupA = groupDao.get(50);
//		Set<UserBean> users = groupDao.getUsers(groupA);
		
		Session s = groupDao.getSession();
        
        GroupBean g = (GroupBean) s.get(GroupBean.class, 50);
        Set<UserBean> users = g.getUsers();
       
        
		for(UserBean user: users){
			LOGGER.warn(user.toString());
			LOGGER.warn(user.getOrg().toString());
		}
		
	   
	}
	
	
   @Test
    public void member_in_admininstration_group_should_belong_to_same_org_as_the_group(){
        //TODO member check administration 
       orgDao.setSession(groupDao.getSession());
       OrgBean org1 = orgDao.get(1);
       List<GroupBean> adminGroupList = orgDao.listAdministrationGroup(org1);
       Assert.assertNotNull(adminGroupList);
       for(GroupBean group:adminGroupList){
           LOGGER.warn("=====Group info====");
           LOGGER.warn(group);
           Assert.assertEquals("Administration", group.getType());
           Set<UserBean> users = group.getUsers();
           for(UserBean user:users){
               LOGGER.warn(user);
               Assert.assertEquals(true,orgDao.contains(org1,user));

           }
       }
    }
	
	

	

	
	@After
	public void tearDown(){
		LOGGER.warn(testName.getMethodName() + " end");
		groupDao.closeSession();
	}
}

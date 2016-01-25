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



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class GroupDaoTest {

	@Autowired 
	private GroupDao groupDao;
	@Autowired
	private UserDao userDao;	
	@Rule 
	public TestName testName = new TestName();
	@Before
	public void setUp(){
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
	
	@Test
	public void testlist() {
		
	}

	@Test 
	public void user_should_belong_only_one_administration_group1(){
		Object[][] data = {
				{50, 1, GroupDao.HAVE_ADMINISTRATION_GROUP},
				{1,  1, GroupDao.DUPLICATED_MEMBER},
				{50, 3, GroupDao.HAVE_ADMINISTRATION_GROUP},
				{50, 4, GroupDao.OK}
		};
		for(int i=0;i<data.length;i++){
			GroupBean groupA = groupDao.get((int)data[i][0]);
			UserBean userA = userDao.get((int)data[i][1]);
			Assert.assertEquals((int)data[i][2], groupDao.addMember(groupA, userA));
		}		
	}
	
	@Test
	public void group_users_can_be_get(){
		System.out.println(testName.getMethodName());
		GroupBean groupA = groupDao.get(50);
		Set<UserBean> users = (Set<UserBean>) groupA.getUsers();
		for(UserBean user: users){
			System.out.println(user);
			System.out.println(user.getOrg());
		}
	}
	
	@Test 
	public void user_should_belong_only_one_administration_group2(){
	
	}
	
	@Test 
	public void user_should_belong_only_one_administration_group3(){
	

	}
	
	@Test 
	public void user_can_be_added_with_group(){
	
	}
	
	@After
	public void tearDown(){
		
	}
}

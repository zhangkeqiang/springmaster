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
		ss.delete(group2);
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

	@Test 
	public void user_should_belong_only_one_administration_group1(){
		System.out.println("=====group+join+grouptype user+====");
		String sql = "SELECT g FROM GroupBean g JOIN g.users u WHERE g.type = :GroupType " +
		" AND u.id = :UserId";
		Query q = ss.createQuery(sql);
		q.setInteger("UserId", 8);
		q.setString("GroupType", "Taskforce");
		GroupBean group = (GroupBean) q.uniqueResult();
		if(group != null)
			System.out.println(group);
		else
			System.out.println("no adminintration group");
	}
	
	@Test 
	public void user_should_belong_only_one_administration_group2(){
		System.out.println("=====user+group+join+grouptype====");
		String sql = "SELECT u FROM UserBean u JOIN u.groups g WHERE g.type = 'Administration' ";
				//GroupBean g JOIN g.users u WHERE "+
		//"g.type = 'Administration' AND u.id = 1";
		Query q = ss.createQuery(sql);
		@SuppressWarnings("unchecked")
		List<UserBean> users = (List<UserBean>) q.list();
		for(UserBean user: users){
			System.out.println(user);
			System.out.println(user.getOrg());
		}
	}
	
	@Test 
	public void user_should_belong_only_one_administration_group3(){
		System.out.println("=====user find groups+join+grouptype user+====");
		UserBean user = (UserBean) ss.byId(UserBean.class).load(1);

		Set<GroupBean> groups = user.getGroups();
		boolean isAdministrationGroupFound = false;
		for(GroupBean group:groups){
			System.out.println(group);
			if(group.getType().equals("Administration")){
				isAdministrationGroupFound = true;
				System.out.println("Found");
				break;
			}
		}
		Assert.assertTrue(isAdministrationGroupFound);

	}
	
	@Test 
	public void user_can_be_added_with_group(){
		UserBean user = new UserBean();
		user.setUserName("李六端");
		ss.beginTransaction();
		GroupBean group = (GroupBean) ss.byId(GroupBean.class).load(1);
		//ss.createCriteria(GroupBean.class);
		HashSet<GroupBean> groups = new HashSet<GroupBean>();
		groups.add(group);
		user.setGroups(groups);
		//user.setAdministrationGroup(group);
		int userNo = (int) ss.save(user);
		ss.getTransaction().commit();
		UserBean userInDB = (UserBean) ss.get(UserBean.class, userNo);

		Assert.assertNotNull(userInDB);
		System.out.println(userInDB);
		ss.beginTransaction();
		ss.delete(userInDB);
		ss.getTransaction().commit();
	}
	
	@After
	public void tearDown(){
		ss.close();
	}
}

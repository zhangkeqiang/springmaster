package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.dao.UserDao;
import com.agilejerry.springmaster.entity.UserBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class UserDaoTest {

	@Autowired
	private UserDao dao;
	@Test
	public void test() {
		UserBean user =new UserBean();
		user.setUserName("李珊珊");
		Assert.assertTrue(dao.create(user) > 0);
		System.out.println(user.getUserNo());
		Assert.assertTrue(dao.delete(user));
	}
	
	
	@Test
	public void list_show_all_user() {
		List<UserBean> list = dao.list();
		for(UserBean user:list){
			System.out.println(user.getUserName()+"   "+user.getUserNo());
			System.out.println(user.getOrgBean());
		}
	}
	

}

package com.agilejerry.springmaster;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.agilejerry.springmaster.entity.UserBean;

import javax.annotation.Resource;

@Component
public class UserDao{
	@Resource
	private SessionFactory sessionFactory;
	
	public boolean create(UserBean user){
		boolean ret = sessionFactory.getCurrentSession().save(user) != null;
		return ret;
	}
}
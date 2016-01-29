package com.agilejerry.springmaster.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

@Repository
public class UserDao{
	@Resource
	private SessionFactory sessionFactory;
	
	public int create(UserBean user){
		int ret = (int) sessionFactory.getCurrentSession().save(user);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBean> list(){
		
		return sessionFactory.getCurrentSession().createQuery("FROM UserBean").list();		
	}

	public boolean delete(UserBean user) {
		try{
		sessionFactory.getCurrentSession().delete(user);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<UserBean> searchByName(String name){
		
		return null;
		
	}

	public UserBean get(int i) {
		return (UserBean)sessionFactory.getCurrentSession().get(UserBean.class, i);
	}

	public boolean checkAdministrationGroupOfUser(UserBean userBean) {
		Set<GroupBean> groups = userBean.getGroups();
		for(GroupBean group:groups){
			if(group.getType().equals("Administration")){
				return true;
			}
		}
		return false;		
	}
}
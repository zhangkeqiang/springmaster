package com.agilejerry.springmaster.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;
import com.agilejerry.springmaster.test.UserDaoTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

@Repository
public class UserDao{
	
	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	@Resource
	private SessionFactory sessionFactory;
	
	public int create(UserBean user){
		int ret = (int) getSession().save(user);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBean> list(){
		
		return getSession().createQuery("FROM UserBean").list();		
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean delete(UserBean user) {
		try{
		getSession().delete(user);
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
		return (UserBean)getSession().get(UserBean.class, i);
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

	public int joinGroup(UserBean user, GroupBean group) {
		log.warn(user.getUserName() +"will join into" + group.getName());
		Set<GroupBean> groupList = user.getGroups();	
		groupList.add(group);
		Session s = getNewSession();
		try{
			s.beginTransaction();
			s.update(user);
			s.getTransaction().commit();
			log.warn(user.getUserName() +"joined into" + group.getName());
		}catch(org.hibernate.NonUniqueObjectException e){
			log.warn(user.getUserName() +"has already joined into" + group.getName());
			return GroupDao.DUPLICATED_MEMBER;
		}catch(Exception e){
			log.error(e.getMessage() + e.getClass());
			return -99;
		}finally{
			s.close();
		}
		
		return 1;
	}

	private Session getNewSession() {		
		return sessionFactory.openSession();
	}

	public boolean isJoined(UserBean user, GroupBean group) {
		Set<GroupBean> groupList = (Set<GroupBean>) user.getGroups();
		ArrayList<Integer> groupIdList = new ArrayList<Integer>();
		for(GroupBean userGroup:groupList){
			groupIdList.add(userGroup.getId());
		}		
		return groupIdList.contains(group.getId());
	}
}
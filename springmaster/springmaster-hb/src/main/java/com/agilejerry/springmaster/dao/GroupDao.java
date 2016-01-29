package com.agilejerry.springmaster.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

@Repository
public class GroupDao{
	public static final int DUPLICATED_MEMBER = -2;

	public static final int OK = 1;

	public static final int HAVE_ADMINISTRATION_GROUP = -1;

	@Resource
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userDao;

	public int create(GroupBean aGroup) {
		return (int) sessionFactory.getCurrentSession().save(aGroup);
	}

	public void update(GroupBean aGroup){
		sessionFactory.getCurrentSession().update(aGroup);
	}

	public GroupBean get(int groupId) {
		return (GroupBean)sessionFactory.getCurrentSession().get(GroupBean.class, groupId);
	}

	public void delete(GroupBean groupBean) {
		sessionFactory.getCurrentSession().delete(groupBean);
		
	}

	public int addMember(GroupBean groupBean, UserBean userBean) {
		if(groupBean.getType().equals("Administration")){
			if(userDao.checkAdministrationGroupOfUser(userBean))
				return HAVE_ADMINISTRATION_GROUP;
		}
		//add group member
		
		if(checkContains(groupBean, userBean))
			return DUPLICATED_MEMBER;
		else{
			Set<UserBean> users = groupBean.getUsers();	
			users.add(userBean);
			update(groupBean);
			
/*			Set<GroupBean> groups = userBean.getGroups();
			groups.add(groupBean);
			sessionFactory.getCurrentSession().update(userBean);*/
			
		}
		return OK;
	}

	private boolean checkContains(GroupBean groupBean, UserBean userBean) {
		String hql = "FROM GroupBean g join g.users u WHERE u.userNo = :UserNo AND g.id = :GroupId";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		q.setInteger("UserNo", userBean.getUserNo());
		q.setInteger("GroupId", groupBean.getId());
		return (q.list().size() > 0);
	}
	

}
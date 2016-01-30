package com.agilejerry.springmaster.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;
import com.agilejerry.springmaster.test.UserDaoTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

@Repository
public class UserDao extends BaseDao{
    
    private static final Logger LOGGER = LogManager.getLogger(UserDaoTest.class);
    
    public int create(UserBean user){
        getSession().beginTransaction();
        int ret = (int) getSession().save(user);
        getSession().getTransaction().commit();
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public List<UserBean> list(){        
        return getSession().createQuery("FROM UserBean").list();        
    }


    public boolean delete(UserBean user) {
        getSession().beginTransaction();
        try{
            getSession().delete(user);
            getSession().getTransaction().commit();
        }catch(Exception e){
            LOGGER.error(e);
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
        UserBean user = get(userBean.getUserNo());
        Set<GroupBean> groups = user.getGroups();
        for(GroupBean group:groups){
            if(group.getType().equals("Administration")){
                return true;
            }
        }
        return false;        
    }

    public int joinGroup(UserBean user, GroupBean group) {
        if(isJoined(user,group)){
            LOGGER.warn(user.getUserName() +"joined into" + group.getName());
            return GroupDao.DUPLICATED_MEMBER;
        }
        LOGGER.warn(user.getUserName() +"will join into" + group.getName());
        Set<GroupBean> groupList = user.getGroups();    
        groupList.add(group);
        return update(user);
    }
    
    public int breakAwayGroup(UserBean user, GroupBean group) {
        LOGGER.warn(user.getUserName() +"will leave " + group.getName());
        Set<GroupBean> groupList = user.getGroups(); 
        for(GroupBean userGroup: groupList){
            if(userGroup.getId() == group.getId()){
                groupList.remove(userGroup);
                update(user);
                break;
            }
        }
        return GroupDao.OK;
    }
    private Session getNewSession() {        
        return sessionFactory.openSession();
    }

    public boolean isJoined(UserBean user, GroupBean group) {
        //UserBean userB = get(user.getUserNo());
        Set<GroupBean> groupList = (Set<GroupBean>) user.getGroups();
        ArrayList<Integer> groupIdList = new ArrayList<Integer>();
        for(GroupBean userGroup:groupList){
            LOGGER.warn(userGroup);
            groupIdList.add(userGroup.getId());
        }        
        return groupIdList.contains(group.getId());
    }



    public int update(UserBean user) {
        Session s = getSession();
        int ret = 0;
        try{
            s.beginTransaction();
            s.update(user);
            s.getTransaction().commit();   
            ret = GroupDao.OK;
        }catch(org.hibernate.NonUniqueObjectException e){
            LOGGER.warn(user.getUserName() +"has already joined that group");
            LOGGER.warn(e.getMessage());
            LOGGER.error(e);
            ret = GroupDao.DUPLICATED_MEMBER;
        }catch(Exception e){
            LOGGER.error(e.getMessage() + e.getClass());
            LOGGER.info(e);
            ret = -99;
        }finally{
           
        }
        return ret;
    }
}
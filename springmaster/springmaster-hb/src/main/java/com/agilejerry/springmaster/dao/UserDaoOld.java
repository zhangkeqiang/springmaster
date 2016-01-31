package com.agilejerry.springmaster.dao;

import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.StateCode;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



@Repository
public class UserDaoOld extends BasicDao{
    
    public static final String ADMINISTRATION = "Administration";
    private static final Logger LOGGER = LogManager.getLogger(UserDaoOld.class);
    
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
        
        return new ArrayList<UserBean>();
        
    }

    public UserBean get(int i) {
        return (UserBean)getSession().get(UserBean.class, i);
    }

    public boolean checkAdministrationGroupOfUser(UserBean userBean) {
        //UserBean user = get(userBean.getUserNo());
        Set<GroupBean> groups = userBean.getGroups();
        for(GroupBean group:groups){
            if(group.getType().equals(ADMINISTRATION)){
                return true;
            }
        }
        return false;        
    }

    public int joinGroup(UserBean user, GroupBean group) {
        if(checkUserJoinGroup(user,group)){
            LOGGER.warn(user.getUserName() +"joined into" + group.getName());
            return StateCode.DUPLICATED_MEMBER;
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
        return StateCode.OK;
    }
    private Session getNewSession() {        
        return sessionFactory.openSession();
    }





    public int update(UserBean user) {
        Session s = getSession();
        int ret = 0;
        try{
            s.beginTransaction();
            s.update(user);
            s.getTransaction().commit();   
            ret = StateCode.OK;
        }catch(org.hibernate.NonUniqueObjectException e){
            s.getTransaction().rollback();
            LOGGER.warn(user.getUserName() +"has already joined that group");
            LOGGER.warn(e.getMessage());
            LOGGER.error(e);
            ret = StateCode.DUPLICATED_MEMBER;
        }catch(Exception e){
            s.getTransaction().rollback();
            LOGGER.error(e.getMessage() + e.getClass());
            LOGGER.info(e);
            ret = -99;
        }
        return ret;
    }

    public boolean checkUserJoinGroup(UserBean userC, GroupBean group) {
        String hql = "FROM UserBean u JOIN u.groups g WHERE u.id = ? AND g.id = ?";
        org.hibernate.Query q = getSession().createQuery(hql);
        q.setParameter(0, userC.getUserNo());
        q.setParameter(1, group.getId());
        return q.list().size()>0;
        
    }
}
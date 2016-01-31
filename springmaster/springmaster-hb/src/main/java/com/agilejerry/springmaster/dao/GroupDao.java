package com.agilejerry.springmaster.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;
import com.agilejerry.springmaster.test.UserDaoTest;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

@Repository
public class GroupDao  extends BasicDao {
    private static final Logger LOGGER = LogManager.getLogger(GroupDao.class);
    public static final int DUPLICATED_MEMBER = -2;

    public static final int OK = 1;

    public static final int HAVE_ADMINISTRATION_GROUP = -1;

    @Autowired
    private UserDaoOld userDao;


    public int create(GroupBean aGroup){
        getSession().beginTransaction();
        int ret = 0;
        try{
            ret = (int) getSession().save(aGroup);
            getSession().getTransaction().commit();
        }catch(ConstraintViolationException e){
            ret = -1023;
            LOGGER.warn(e);
        }
        catch(Exception e){
            ret = -99;
            LOGGER.warn(e);
        }
        return ret;
    }

    public void update(GroupBean aGroup) {
        getSession().beginTransaction();
        getSession().update(aGroup);
        getSession().getTransaction().commit();
    }

    public GroupBean get(int groupId) {
        return (GroupBean) getSession().get(GroupBean.class, groupId);
    }

    public void delete(GroupBean groupBean) {
        getSession().beginTransaction();
        getSession().delete(groupBean);
        getSession().getTransaction().commit();
    }

    public int addMember(GroupBean group, UserBean userBean) {
        Session ss = getSession(); 
        int ret = 0;
        try {
            if (group.getType().equals("Administration")) {
                userDao.setSession(getSession());
                if (userDao.checkAdministrationGroupOfUser(userBean)) {
                    LOGGER.debug("====HAVE_ADMINISTRATION_GROUP====");
                    return HAVE_ADMINISTRATION_GROUP;
                }
            }
            LOGGER.debug("NOT_ADMINISTRATION_GROUP");
            if (checkContains(group, userBean)){
                LOGGER.debug("====duplicated====");
                return DUPLICATED_MEMBER;
            }
            else {
               // add group member
                LOGGER.debug("====add member====");                
                Set<UserBean> users = group.getUsers();
                LOGGER.debug("====get members====");
                users.add(userBean);
                update(group);
                ret = GroupDao.OK;
            }
        } catch (Exception e) {
            ret = -99;
            LOGGER.error(e);
        }        
        return ret;
    }

    private boolean checkContains(GroupBean groupBean, UserBean userBean) {
        String hql = "FROM GroupBean g join g.users u WHERE u.userNo = :UserNo AND g.id = :GroupId";
        Query q = getSession().createQuery(hql);
        q.setInteger("UserNo", userBean.getUserNo());
        q.setInteger("GroupId", groupBean.getId());
        return q.list().size() > 0;
        
    }

    public void removeMember(GroupBean group, UserBean user) {
        Set<UserBean> users = group.getUsers();
        for (UserBean userInGroup : users) {
            if (user.getUserNo() == userInGroup.getUserNo()) {
                users.remove(userInGroup);
                update(group);
                break;
            }
        }
    }



}
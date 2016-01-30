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


public abstract class BaseDao{
    private static final Logger LOGGER = LogManager.getLogger(UserDaoTest.class);
    @Resource
    protected SessionFactory sessionFactory;
    protected Session session;
    
    public void setSession(Session session){
        this.session = session;
    }
    public Session getSession(){
//        Session currSession = sessionFactory.getCurrentSession();
//        if(currSession != null && currSession.isConnected() && currSession.isOpen()){
//            LOGGER.debug("getCurrentSession");
//            LOGGER.debug(currSession);
//            return currSession;
//        }
        if(session == null){
            session = sessionFactory.openSession();
            LOGGER.debug("open session from null");
            return session;
        }
        if(session.isConnected()){
            LOGGER.debug("current session");
        }else{
            LOGGER.debug("open session from not connected");
            session = sessionFactory.openSession();
        }
        return session;
    }
    
    public void closeSession(){
        LOGGER.debug("close session");
        if(session!=null)
            session.close();
    }
   
}
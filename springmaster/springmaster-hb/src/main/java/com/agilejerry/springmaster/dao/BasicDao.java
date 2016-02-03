package com.agilejerry.springmaster.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  




import javax.annotation.Resource;


public abstract class BasicDao{
    private static final Logger LOGGER = LogManager.getLogger(BasicDao.class);
    @Resource
    protected SessionFactory sessionFactory;
    protected Session session;
    
    public void setSession(Session session){
        this.session = session;
    }
    public Session getSession(){
        if(session == null){
            session = sessionFactory.openSession();
            LOGGER.debug("open session from null");
            return session;
        }
        if(session.isConnected()){
            LOGGER.debug("existed session");
        }else{
            LOGGER.debug("open session from not connected");
            session = sessionFactory.openSession();
        }
        return session;
    }
    
    public void closeSession(){
        LOGGER.debug("close session");
        if(session!=null && session.isOpen())
            session.close();
    }
   
}
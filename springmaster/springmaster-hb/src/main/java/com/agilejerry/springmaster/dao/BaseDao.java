package com.agilejerry.springmaster.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;

import com.agilejerry.springmaster.entity.UserBean;
import com.agilejerry.springmaster.test.UserDaoTest;
import com.agilejerry.util.GenericUtil;

import java.util.List;

import javax.annotation.Resource;


public abstract class BaseDao<E>{
    private static final Logger LOGGER = LogManager.getLogger(BaseDao.class);
    
    protected Class<E> clazz;
    
    @Resource
    protected SessionFactory sessionFactory;
    protected Session session;
    
    @SuppressWarnings("unchecked")
    public BaseDao() {
        try {
            clazz = GenericUtil.getActualClass(this.getClass(), 0);
        } catch (Exception e) {
            LOGGER.error("base dao can not get  clazz!", e);
        }
    }
    

    public void save(E entity) throws Exception {
        this.getSession().saveOrUpdate(entity);
    }

    public int create(E entity){
        getSession().beginTransaction();
        int ret = (int) getSession().save(entity);
        getSession().getTransaction().commit();
        return ret;
    }
    public boolean delete(E entity) {
        getSession().beginTransaction();
        try{
            getSession().delete(entity);
            getSession().getTransaction().commit();
        }catch(Exception e){
            getSession().getTransaction().rollback();
            LOGGER.error(e);
            return false;
        }
        return true;
    }
    
    public List<E> list() {
        String hql = "from " + this.clazz.getName();
        return this.getSession().createQuery(hql).list();
    }
    
    public List<E> list(String hql) {
        return this.getSession().createQuery(hql).list();
    }
    
    public E get(Integer id) {
        return (E) this.getSession().get(this.clazz, id);
    }
    
    public int update(E entity) {
        Session s = getSession();
        int ret = 0;
        try{
            s.beginTransaction();
            s.update(entity);
            s.getTransaction().commit();   
            ret = GroupDao.OK;
        }catch(org.hibernate.NonUniqueObjectException e){
            s.getTransaction().rollback();
            LOGGER.error(e);
            ret = GroupDao.DUPLICATED_MEMBER;
        }catch(Exception e){
            s.getTransaction().rollback();
            LOGGER.error(e.getMessage() + e.getClass());
            LOGGER.error(e);
            ret = -99;
        }
        return ret;
    }
    public void setSession(Session session){
        this.session = session;
    }
    public Session getSession(){
//        Session currSession = sessionFactory.getCurrentSession();
//        if(currSession !=null && currSession.isConnected() && currSession.isOpen()){
//            LOGGER.debug("getCurrentSession");
//            return currSession;
//        }
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
    
    public void flushSession(){
        LOGGER.debug("flush session");
        if(session!=null)
            session.flush();
    }
   
}
package com.agilejerry.springmaster.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;

import com.agilejerry.springmaster.StateCode;
import com.agilejerry.util.GenericUtil;

import java.util.List;

import javax.annotation.Resource;

@Transactional
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
            LOGGER.error("base dao can not get clazz!", e);
        }
    }
    

    public void save(E entity){
        this.getSession().saveOrUpdate(entity);
    }

    public int create(E entity){
        int ret = StateCode.ERROR;
        try{
            ret = (int) getSession().save(entity);
        }catch(ConstraintViolationException e){
            ret = StateCode.FIELD_NEEDED;
            LOGGER.error("ConstraintViolationException");
            LOGGER.error(e);
            getSession().clear();
        }catch(Exception e){
            LOGGER.error(e);
        }
        return ret;
    }
    public boolean delete(E entity) {
        try{
            getSession().delete(entity);
        }catch(Exception e){
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
            s.update(entity);
            ret = StateCode.OK;
        }catch(org.hibernate.NonUniqueObjectException e){
            s.clear();
            LOGGER.error(e);
            ret = StateCode.DUPLICATED_MEMBER;
        }catch(Exception e){
            s.clear();
            LOGGER.error(e.getMessage() + e.getClass());
            LOGGER.error(e);
            ret = StateCode.ERROR;
        }
        return ret;
    }
    public void setSession(Session session){
        this.session = session;
    }
    
    public Session getSession(){
        try{
            Session currSession = sessionFactory.getCurrentSession();
            LOGGER.debug("getCurrentSession-AutomaticOne");
            return currSession;
        }catch(HibernateException e) {
            LOGGER.error(e);
            return null;
        } 
    }


    protected Session getNonAutomaticSession() {
        if(session == null){
            session = sessionFactory.openSession();
            LOGGER.debug("getNonAutomaticSession-open session from null");
            return session;
        }
        if(session.isConnected() && session.isOpen()){
            LOGGER.debug("getNonAutomaticSession-existed session");
        }else{
            LOGGER.debug("getNonAutomaticSession-open session from not connected");
            session = sessionFactory.openSession();
        }
        return session;
    }
    
    public void closeSession(){
        LOGGER.debug("closing NonAutomatic session");
        if(session!=null && session.isOpen())
            session.close();
        else
            LOGGER.debug("NonAutomatic session is no need to close");
    }
    
    public void flushNonAutomaticSession(){
        LOGGER.debug("flush NonAutomatic session");
        if(session!=null)
            session.flush();
    }
   
}
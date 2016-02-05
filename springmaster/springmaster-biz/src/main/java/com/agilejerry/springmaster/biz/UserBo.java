package com.agilejerry.springmaster.biz;

import java.util.Set;

import com.agilejerry.springmaster.dao.UserDao;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

public class UserBo extends UserBean{
    private static final long serialVersionUID = -1632143212679880348L;    
    private UserDao dao;
    private UserBean userBean;
    public boolean isAdministrationGroupLeader() {
        if(userBean == null)
            userBean = dao.get(this.getUserNo());
        Set<GroupBean> groups = userBean.getGroups();
        for(GroupBean group:groups){
            if(group.getType().equals("Administration")){
                if(group.getLeader() == null)
                    continue;
                if(userBean.getUserNo() == group.getLeader().getUserNo()){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * dao
     *
     * @return  the dao
     * @since   1.0.0
    */
    
    public UserDao getDao() {
        return dao;
    }
    /**
     * @param dao the dao to set
     */
    public void setDao(UserDao dao) {
        this.dao = dao;
    }
    /**
     * userBean
     *
     * @return  the userBean
     * @since   1.0.0
    */
    
    public UserBean getUserBean() {
        return userBean;
    }
    /**
     * @param userBean the userBean to set
     */
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

}

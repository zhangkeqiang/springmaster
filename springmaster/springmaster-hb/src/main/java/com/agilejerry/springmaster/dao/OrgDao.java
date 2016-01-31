package com.agilejerry.springmaster.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;

@Repository
public class OrgDao extends BaseDao<OrgBean> {
    private static final Logger LOGGER = LogManager.getLogger(OrgDao.class);

    public OrgBean getDefault() {
        return get(1);
    }

    public List<GroupBean> listAdministrationGroup(OrgBean orgBean) {
        Set<GroupBean> groupSet = orgBean.getGroups();
        List<GroupBean> groupList = new ArrayList<GroupBean>();
        for(GroupBean group: groupSet){
            if("Administration".equals(group.getType()))
                groupList.add(group);
        }
        return groupList;
    }

    public boolean contains(OrgBean orgBean, UserBean userBean) {        
        return orgBean.getUsers().contains(userBean);
    }

    public void enroll(OrgBean orgBean, UserBean userBean) {
        userBean.setOrg(orgBean);
        getSession().beginTransaction();
        getSession().update(userBean);
        getSession().getTransaction().commit();
    }
   

}

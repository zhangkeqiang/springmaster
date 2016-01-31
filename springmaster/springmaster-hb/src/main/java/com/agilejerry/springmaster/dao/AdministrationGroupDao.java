package com.agilejerry.springmaster.dao;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.agilejerry.springmaster.Const;
import com.agilejerry.springmaster.StateCode;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

@Repository
public class AdministrationGroupDao extends GroupDao {
    private static final Logger LOGGER = LogManager.getLogger(AdministrationGroupDao.class);

    @Override
    public int addMember(GroupBean group, UserBean userBean){
        if(group == null || userBean == null)
            return StateCode.NULL;
        if(!Const.ADMINISTRATION.equals(group.getType()))
            return StateCode.NOT_ADMINISTRATION_GROUP;
        userDao.setSession(getSession());
        Set<GroupBean> groups = userBean.getGroups();
        for(GroupBean userGroup:groups){
            if(Const.ADMINISTRATION.equals(userGroup.getType())){
                if(userGroup.getId() != group.getId()){
                    groups.remove(userGroup);
                    break;
                }else{
                    return StateCode.OK;
                }
            }
        }
        groups.add(group);
        return userDao.update(userBean);

        
    }



}

package com.agilejerry.springmaster.dao;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.agilejerry.springmaster.Const;
import com.agilejerry.springmaster.StateCode;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

@Repository
@Transactional
public class UserDao extends BaseDao<UserBean> {
    private static final Logger LOGGER = LogManager.getLogger(BaseDao.class);


    public boolean checkUserJoinAdministrationGroup(UserBean user) {
        Set<GroupBean> groups = user.getGroups();
        for(GroupBean group:groups){
            if(group.getType().equals(Const.ADMINISTRATION)){
                return true;
            }
        }
        return false; 
    }

    public int joinGroup(UserBean user, GroupBean group) {
        if(checkUserJoinGroup(user,group)){
            LOGGER.warn(user.getUserName() +"has already joined into" + group.getName());
            return StateCode.DUPLICATED_MEMBER;
        }
        LOGGER.warn(user.getUserName() +"will join into" + group.getName());
        Set<GroupBean> groupList = user.getGroups();    
        groupList.add(group);
        return update(user);
    }
    
    public boolean checkUserJoinGroup(UserBean userC, GroupBean group) {
        String hql = "FROM UserBean u JOIN u.groups g WHERE u.id = ? AND g.id = ?";
        org.hibernate.Query q = getSession().createQuery(hql);
        q.setParameter(0, userC.getUserNo());
        q.setParameter(1, group.getId());
        return q.list().size()>0;
        
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

    public GroupBean getAdministrationGroup(UserBean user) {
        Set<GroupBean> groups = user.getGroups();
        for(GroupBean group:groups){
            if(group.getType().equals(Const.ADMINISTRATION)){
                return group;
            }
        }
        return null;
    }

}

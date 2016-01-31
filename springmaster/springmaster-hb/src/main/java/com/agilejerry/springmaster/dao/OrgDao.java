package com.agilejerry.springmaster.dao;

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
   

}

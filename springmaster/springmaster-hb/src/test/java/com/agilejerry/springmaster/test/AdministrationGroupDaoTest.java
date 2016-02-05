package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.agilejerry.springmaster.StateCode;
import com.agilejerry.springmaster.dao.AdministrationGroupDao;
import com.agilejerry.springmaster.dao.GroupDao;

import com.agilejerry.springmaster.dao.OrgDao;
import com.agilejerry.springmaster.dao.UserDao;

import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:testwithdb.xml") 
@Transactional
public class AdministrationGroupDaoTest {
    private static final Logger LOGGER = LogManager.getLogger(AdministrationGroupDaoTest.class);

    @Autowired
    private AdministrationGroupDao adGroupDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgDao orgDao;
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        LOGGER.warn(testName.getMethodName() + " Start...");
        Assert.assertNotNull(adGroupDao);
    }

    int[][] data = { 
            { 2, 1, StateCode.OK, -2 },
//            { 50, 3, StateCode.OK, -1 }, 
//            { 51, 4, StateCode.OK, -1 },
//            { 6, 2, StateCode.OK, 1 }, 
//            { 6, 3, StateCode.OK, 1 } 
            };

    @Test
    public void add_member_to_ad_group_need_move_if_already_in_another_ad_group() {
        for(int i=0;i<data.length;i++){
            UserBean user = userDao.get(data[i][1]);
            GroupBean group = adGroupDao.get(data[i][0]);
            GroupBean userAdGroup = userDao.getAdministrationGroup(user);
            LOGGER.info(userAdGroup);
            int ret = adGroupDao.addMember(group, user);
            Assert.assertEquals(data[i][2], ret);
            if(data[i][2] == StateCode.NOT_ADMINISTRATION_GROUP){
                continue;
            }
            Assert.assertTrue(adGroupDao.checkContains(group, user));
            adGroupDao.removeMember(group, user);
            Assert.assertFalse(adGroupDao.checkContains(group, user));
            if (userAdGroup != null) {
                LOGGER.info(userAdGroup);
                ret = adGroupDao.addMember(userAdGroup, user);
                LOGGER.warn(ret);
                Assert.assertTrue(adGroupDao.checkContains(userAdGroup, user));
            }
        }

    }

    // groupmemberbean to OneToMany and ManyToOne to replace ManyToMany

    @Test
    public void member_in_admininstration_group_should_belong_to_same_org_as_the_group() {
        // member check administration
        OrgBean org1 = orgDao.get(1);
        List<GroupBean> adminGroupList = orgDao.listAdministrationGroup(org1);
        Assert.assertNotNull(adminGroupList);
        for (GroupBean group : adminGroupList) {
            LOGGER.warn("=====Group info====");
            LOGGER.warn(group);
            Assert.assertEquals("Administration", group.getType());
            Set<UserBean> users = group.getUsers();
            for (UserBean user : users) {
                LOGGER.warn(user);
                Assert.assertEquals(true, orgDao.contains(org1, user));

            }
        }
    }

    @After
    public void tearDown() {
        LOGGER.warn(testName.getMethodName() + " end");
        adGroupDao.closeSession();
    }
}

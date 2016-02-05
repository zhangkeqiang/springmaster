package com.agilejerry.springmaster.biz.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.agilejerry.springmaster.biz.UserBo;
import com.agilejerry.springmaster.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/hibernate.xml"}) 
@Transactional
public class UserBoTest {
    @Autowired
    private UserDao userDao;
    UserBo userBo;
    @Before
    public void setUp() throws Exception {
        userBo = new UserBo();
        Assert.assertNotNull(userDao);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_init() {
        userBo.setUserName("zhangsan");
        Assert.assertEquals("zhangsan", userBo.getUserName());
    }
    
    @Test
    public void userBo_should_know_whether_is_a_admin_group_leader() {
        userBo.setUserNo(1);
        userBo.setDao(userDao);
        Assert.assertTrue(userBo.isAdministrationGroupLeader());
    }
    
    

}

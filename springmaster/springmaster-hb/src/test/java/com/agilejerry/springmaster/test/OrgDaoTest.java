package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.StateCode;
import com.agilejerry.springmaster.dao.GroupDaoOld;
import com.agilejerry.springmaster.dao.OrgDao;
import com.agilejerry.springmaster.dao.UserDaoOld;
import com.agilejerry.springmaster.dao.UserDao;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.OrgBean;
import com.agilejerry.springmaster.entity.UserBean;
  

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class OrgDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(OrgDaoTest.class);
	@Rule 
	public TestName testName = new TestName();
	
	@Autowired
	private OrgDao orgDao;
	@Autowired
    private UserDaoOld userDao;
	@Autowired
	private GroupDaoOld groupDao;
	@Before
	public void setUp(){
		LOGGER.warn(testName.getMethodName() + " Start...");		
	}
	
	@After
	public void tearDown(){
		LOGGER.warn(testName.getMethodName() + " end");
		orgDao.closeSession();
	}
	
   @Test
    public void init_test(){
        Assert.assertNotNull(orgDao);
    }
   
	@Test
	public void test_list_org_and_member(){
	    LOGGER.warn("list all org=======");
	    List<OrgBean> list = orgDao.list();
	    LOGGER.warn("list all org");
	    for(OrgBean org:list){
	        LOGGER.info(org); 
	        Set<UserBean> users = org.getUsers();
	        for(UserBean user:users){
	            LOGGER.info(user);
	        }
	    }
	}
	
	@Test 
	public void test_change_org(){
	    OrgBean org = orgDao.get(1);
	    userDao.setSession(orgDao.getSession());
	    UserBean userA = userDao.get(4);
	    OrgBean userOldOrg = userA.getOrg();
	    userA.setOrg(org);
	    
	    orgDao.flushSession();
	    UserBean userB = userDao.get(4);
	    Assert.assertEquals(org.getName(), userB.getOrg().getName());	    
	    userB.setOrg(userOldOrg);
	    userDao.update(userB);
	}
	
	@Test
	public void an_administration_group_must_belong_to_an_org(){
	    GroupBean group = new GroupBean();
	    group.setName("企划组");
	    group.setType("Administration");
	    int ret;
	    groupDao.setSession(orgDao.getSession());
        ret = groupDao.create(group);
        Assert.assertEquals(StateCode.ORG_IS_NOT_SET, ret);	  
	    LOGGER.warn("Set org, then Re-Create it");
	    
	    group = new GroupBean();
        group.setName("企划组");
        group.setType("Administration");
	    group.setOrg(orgDao.get(2));
	    ret = groupDao.create(group);
	    LOGGER.warn(ret);
        Assert.assertEquals(true, ret > 10);
        
        Assert.assertEquals(StateCode.OK, groupDao.delete(group));
	    
	}
	
	@Test
	public void test_find_administration_group_in_org(){
	    String hql = "SELECT G FROM GroupBean G JOIN G.org o with o.name in(:Names) WHERE G.type = :Type";
	    Query q = orgDao.getSession().createQuery(hql);
	    q.setParameter("Type", "Administration");
	    q.setParameterList("Names", new String[]{"微软公司","IBM公司"});
	    List<GroupBean> groupList = q.list();
	    Assert.assertTrue(groupList.size()>1);
	    for(GroupBean group:groupList){
	        LOGGER.warn(group);
	    }
	}
	

	
	   @Test
	    public void an_administration_group_must_belong_to_an_org2(){
	        GroupBean group = new GroupBean();
	        group.setName("企划组");
	        group.setType("Administration");
	        int ret;
	        groupDao.setSession(orgDao.getSession());
	        ret = groupDao.create(group);
	        Assert.assertEquals(StateCode.ORG_IS_NOT_SET, ret);   
	        LOGGER.warn("Set org, then Re-Create it");
	        
	        OrgBean mockOrg = mock(OrgBean.class);
	        when(mockOrg.getName()).thenReturn("气候部");
	        when(mockOrg.getId()).thenReturn(2);
	        
	        group = new GroupBean();
	        group.setName("气候组");
	        group.setType("Administration");
	        group.setOrg(mockOrg);
	        ret = groupDao.create(group);
	        LOGGER.warn(ret);
	        Assert.assertEquals(true, ret > 10);
	        
	        verify(mockOrg,atLeast(1)).getId();
	        verify(mockOrg,times(0)).getName();
	        Assert.assertEquals(StateCode.OK, groupDao.delete(group));
	        
	    }
	
}

	


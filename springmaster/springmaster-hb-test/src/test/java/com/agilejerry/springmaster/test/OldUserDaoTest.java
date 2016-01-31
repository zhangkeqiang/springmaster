package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;

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
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.StateCode;
import com.agilejerry.springmaster.dao.GroupDaoOld;
import com.agilejerry.springmaster.dao.UserDaoOld;
import com.agilejerry.springmaster.entity.GroupBean;
import com.agilejerry.springmaster.entity.UserBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:withhibernate.xml") 
public class OldUserDaoTest {
	private static final Logger log = LogManager.getLogger(OldUserDaoTest.class);
	@Rule 
	public TestName testName = new TestName();
	@Autowired
	private UserDaoOld userDao;
	@Autowired 
	private GroupDaoOld groupDao;
	@Before
	public void setUp(){
		log.warn(testName.getMethodName() + " Start...");	
		userDao.getSession().clear();
	}
	
	@After
	public void tearDown(){
		log.warn(testName.getMethodName() + " end");
		userDao.closeSession();
		//clear the changed or added data
//        for(int i=0;i<data.length;i++){
//            if(data[i][2] == GroupDao.OK){
//                // new group member need be removed
//              GroupBean group = groupDao.get(data[i][0]);
//              UserBean user = userDao.get(data[i][1]);
//              int ret = userDao.breakAwayGroup(user, group);
//            }
//        }
	}
	@Test
	public void testUserCRUD() {
		UserBean user =new UserBean();
		user.setUserName("李珊珊1234");
		Assert.assertTrue(userDao.create(user) > 0);
		log.warn(user);
		Assert.assertTrue(userDao.delete(user));
		
	}
	
	@Test
	public void testcheckAdministrationGroupOfUser(){
	    //userDao.setSession(groupDao.getSession());
	    int[] userids = {2,
	            3,
	            4
	            };
	    for(int i=0;i<userids.length;i++){
	        UserBean user = (UserBean) userDao.get(userids[i]);
	        log.info(user.getUserName());
	        Assert.assertNotNull(user);
	        Assert.assertTrue(userDao.checkAdministrationGroupOfUser(user));
	       
	    }
	  
	    
	    
	}
	
	@Test
	public void list_show_all_user() {
		List<UserBean> list = userDao.list();		
		for(UserBean user:list){
			log.warn(user);
			log.warn(user.getOrg());
			Set<GroupBean> groups = user.getGroups();
			for(GroupBean group:groups){
				log.warn(group.toString());
			}
		}
	}
	
    int[][] data = {
            {1,2,StateCode.OK},
            {1,3,StateCode.OK},
            {1,8,StateCode.DUPLICATED_MEMBER},
            {50,8,StateCode.OK},
    };
    
	@Test
	public void user_can_join_to_one_group(){		
		//get group from groupDao 

		for(int i=0;i<data.length;i++){
			GroupBean group = groupDao.get(data[i][0]);
			Assert.assertNotNull(group);
			log.warn(group);
			Session ss = groupDao.getSession();
//			Session ss = userDao.getSession();
			UserBean user = (UserBean) ss.get(UserBean.class, data[i][1]);
			log.warn(user);
			Assert.assertNotNull(user);
			Set<GroupBean> groups = user.getGroups();
			for(GroupBean userGroup:groups){
				log.warn(userGroup.toString());
			}
			userDao.setSession(ss);
			int ret = userDao.joinGroup(user, group);
			Assert.assertEquals(data[i][2],ret);
			log.warn("List groups after join group");
			for(GroupBean userGroup:groups){
				log.warn(userGroup.toString());
			}
			UserBean userB = userDao.get(data[i][1]);
			boolean isJoined = userDao.checkUserJoinGroup(userB,group);
			Assert.assertTrue(isJoined);
		}
		
		//clear the changed or added data
	      for(int i=0;i<data.length;i++){
	          if(data[i][2] == StateCode.OK){
	              // new group member need be removed
	            GroupBean group = groupDao.get(data[i][0]);
	            UserBean user = userDao.get(data[i][1]);
	            
	            Set<GroupBean> groups = user.getGroups();
	            for(GroupBean userGroup:groups){
	                log.warn(userGroup.toString());
	            }
	            int ret = userDao.breakAwayGroup(user, group);
	            Assert.assertEquals(data[i][2],ret);
	            log.warn("List groups after leave group");
	            for(GroupBean userGroup:groups){
	                log.warn(userGroup.toString());
	            }
	            UserBean userB = userDao.get(data[i][1]);
	            boolean isJoined = userDao.checkUserJoinGroup(userB,group);
	            Assert.assertFalse(isJoined);
	          }
	      }
	}
	

	

}

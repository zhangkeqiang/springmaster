package com.agilejerry.springmaster.dailytest;

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
	private UserDaoOld userDaoOld;
	@Autowired 
	private GroupDaoOld groupDao;
	@Before
	public void setUp(){
		log.warn(testName.getMethodName() + " Start...");	
		//userDaoOld.getSession().clear();
	}
	
	@After
	public void tearDown(){
		log.warn(testName.getMethodName() + " end");
		userDaoOld.closeSession();
	}
	@Test
	public void testUserCRUD() {
		UserBean user =new UserBean();
		user.setUserName("李珊珊1234");
		Assert.assertTrue(userDaoOld.create(user) > 0);
		log.warn(user);
		Assert.assertTrue(userDaoOld.delete(user));
		
	}
	
	@Test
	public void testcheckAdministrationGroupOfUser(){
	    userDaoOld.setSession(groupDao.getSession());
	    int[] userids = {1,
//	            3,
//	            4
	            };
	    for(int i=0;i<userids.length;i++){
	        UserBean user = (UserBean) userDaoOld.get(userids[i]);
	        log.info(user.getUserName());
	        Assert.assertNotNull(user);
	        Assert.assertTrue(userDaoOld.checkAdministrationGroupOfUser(user));
	       
	    }
	  
	    
	    
	}
	
	@Test
	public void list_show_all_user() {
		List<UserBean> list = userDaoOld.list();		
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
            {1,1,StateCode.DUPLICATED_MEMBER},
//            {1,3,StateCode.OK},
//            {1,8,StateCode.DUPLICATED_MEMBER},
//            {50,8,StateCode.OK},
    };
    
	@Test
	public void user_can_join_to_one_group(){		
		//get group from groupDao 

		for(int i=0;i<data.length;i++){
		    groupDao.setSession(userDaoOld.getSession());
			GroupBean group = groupDao.get(data[i][0]);
			Assert.assertNotNull(group);
			log.warn(group);
			Session ss = userDaoOld.getSession();
			UserBean user = (UserBean) ss.get(UserBean.class, data[i][1]);
			log.warn(user);
			Assert.assertNotNull(user);
			Set<GroupBean> groups = user.getGroups();
			for(GroupBean userGroup:groups){
				log.warn(userGroup.toString());
			}
			int ret = userDaoOld.joinGroup(user, group);
			Assert.assertEquals(data[i][2],ret);
			log.warn("List groups after join group");
			for(GroupBean userGroup:groups){
				log.warn(userGroup.toString());
			}
			UserBean userB = userDaoOld.get(data[i][1]);
			boolean isJoined = userDaoOld.checkUserJoinGroup(userB,group);
			Assert.assertTrue(isJoined);
		}
		
		//clear the changed or added data
	      for(int i=0;i<data.length;i++){
	          if(data[i][2] == StateCode.OK){
	              // new group member need be removed
	            GroupBean group = groupDao.get(data[i][0]);
	            UserBean user = userDaoOld.get(data[i][1]);
	            
	            Set<GroupBean> groups = user.getGroups();
	            for(GroupBean userGroup:groups){
	                log.warn(userGroup.toString());
	            }
	            int ret = userDaoOld.breakAwayGroup(user, group);
	            Assert.assertEquals(data[i][2],ret);
	            log.warn("List groups after leave group");
	            for(GroupBean userGroup:groups){
	                log.warn(userGroup.toString());
	            }
	            UserBean userB = userDaoOld.get(data[i][1]);
	            boolean isJoined = userDaoOld.checkUserJoinGroup(userB,group);
	            Assert.assertFalse(isJoined);
	          }
	      }
	}
	

	

}

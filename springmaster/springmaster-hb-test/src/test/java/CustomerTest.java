

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.Customer;
import com.agilejerry.springmaster.Greeter;
import com.agilejerry.springmaster.Menu;
import com.agilejerry.springmaster.impl.MiddleGradeMenu;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:customerTest.xml") 
public class CustomerTest {
	
	@Autowired 
	private Customer customerA;
	
	//@Autowired 
	@Resource
	private Greeter greeterA;
	
	@Autowired
	@Qualifier("HighGradeMenu")
	private Menu highGradeMenu;
	
	
	
	@Autowired
	@Qualifier("MiddleGradeMenu")
	private Menu middleGradeMenu;
	
	@Test
	public void testInit(){
		ApplicationContext context = new ClassPathXmlApplicationContext(   
                "customerTest.xml"); 
		Greeter greeter = (Greeter) context.getBean("Greeter");
		Customer customer = (Customer) context.getBean("Customer");
		Assert.assertNotNull(greeter);
		Assert.assertNotNull(customer.getGreeter());
		Assert.assertEquals("Mike", greeter.getName());
		Assert.assertEquals("Jerry", customer.getName());
		Assert.assertEquals(greeter, customer.getGreeter());
		
	}
	
	@Test 
	public void autowired_class_should_be_instantiated(){
		Assert.assertNotNull(customerA);
		Assert.assertNotNull(greeterA);
		Assert.assertNotNull(customerA.getWallet());
		Assert.assertNotNull(highGradeMenu);
	}
	
	@Test
	public void highGradeMenu_is_High_Grade(){
		Assert.assertEquals("High Grade", highGradeMenu.getGrade());
	}

	@Test
	public void middleGradeMenu_is_Middle_Grade(){
		Assert.assertEquals("Middle Grade", middleGradeMenu.getGrade());
	}
	
	
}

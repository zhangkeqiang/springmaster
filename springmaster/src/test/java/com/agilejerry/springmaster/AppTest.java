package com.agilejerry.springmaster;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class AppTest 
{


    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp()
    {
        assertTrue( true );
    }
    
	@Test
	public void contextLoads() {
		
	}
}

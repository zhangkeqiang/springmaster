package com.agilejerry.springmaster.service.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.service.EventAssistService;
import com.agilejerry.springmaster.service.EventService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationTestContext.xml") 
public class EventServiceWithInjectMocksTest extends AbstractJUnit4SpringContextTests{

    @InjectMocks
    @Autowired
    EventService eventService;

    @Mock 
    EventAssistService eventAssistService;

    @Before
    public void setup() {
        Assert.assertNotNull(eventService);    
        System.out.println(this.applicationContext.getBean("eventAssistService").toString());
        MockitoAnnotations.initMocks(this);
        when(eventAssistService.calcByAssist(2)).thenReturn(2);
        System.out.println("TEST BEGIN");
        System.out.println(this.applicationContext.getBean("eventAssistService").toString());
        System.out.println(eventAssistService.toString());
    }
    
    @After
    public void tearDown(){
        eventAssistService = null; 
        System.out.println(this.applicationContext.getBean("eventAssistService").toString());
    }

    @Test
    public void testWithMockAssist(){
        Assert.assertEquals(2, eventService.calc(1));
        verify(eventAssistService).calcByAssist(2);  
        verify(eventAssistService, times(1)).calcByAssist(2);
        System.out.println(this.applicationContext.getApplicationName());
    }
 
    @Test
    public void mockObject_observe(){
        Assert.assertSame(eventAssistService, this.applicationContext.getBean("eventAssistService"));
    }

}

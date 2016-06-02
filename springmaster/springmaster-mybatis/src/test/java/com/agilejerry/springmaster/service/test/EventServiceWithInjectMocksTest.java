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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.service.EventAssistService;
import com.agilejerry.springmaster.service.EventService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationTestWithMockContext.xml") 
public class EventServiceWithInjectMocksTest extends AbstractJUnit4SpringContextTests{

    private final Logger log = LogManager.getLogger(this);
    @InjectMocks
    @Autowired
    EventService eventService;

    @Mock 
    EventAssistService eventAssistService;

    @Before
    public void setup() {
        Assert.assertNotNull(eventService);    
        log.info(this.applicationContext.getBean("eventAssistService").toString());
        MockitoAnnotations.initMocks(this);
        when(eventAssistService.calcByAssist(2)).thenReturn(2);
        log.info("TEST BEGIN");
        log.info(this.applicationContext.getBean("eventAssistService").toString());
        log.info(eventAssistService.toString());
        
    }
    
    @After
    public void tearDown(){
        eventAssistService = null; 
        log.info(this.applicationContext.getBean("eventAssistService").toString());
    }

    @Test
    public void testWithMockAssist(){
        Assert.assertEquals(2, eventService.calc(1));
        verify(eventAssistService).calcByAssist(2);  
        verify(eventAssistService, times(1)).calcByAssist(2);
        log.info(this.applicationContext);
    }
 
    @Test
    public void mockObject_observe(){
        Assert.assertNotSame(eventAssistService, this.applicationContext.getBean("eventAssistService"));
    }

}

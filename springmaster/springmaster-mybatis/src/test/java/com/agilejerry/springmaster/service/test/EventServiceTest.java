package com.agilejerry.springmaster.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.service.EventService;
import com.agilejerry.springmaster.test.BaseTestCase;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationTestWithMockContext.xml") 
public class EventServiceTest {

    private final Logger log = LogManager.getLogger(EventServiceTest.class);
    @Autowired
    EventService eventService;

    @Mock
    EventService mockEventService;

    @Before
    public void setup() {
        Assert.assertNotNull(eventService);
        
        MockitoAnnotations.initMocks(this);
        when(mockEventService.calc(102)).thenReturn(11);
    }

    @Test
    public void testReal(){
        Assert.assertEquals(28, eventService.calc(1));
        Assert.assertEquals(64, eventService.calc(2));
    }
    @Test
    public void testMock() {
        Assert.assertEquals(11, mockEventService.calc(102));
        Assert.assertEquals(0, mockEventService.calc(100));
        log.warn("hello");
    }

}

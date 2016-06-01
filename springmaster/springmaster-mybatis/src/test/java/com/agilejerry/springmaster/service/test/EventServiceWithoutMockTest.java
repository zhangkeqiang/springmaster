package com.agilejerry.springmaster.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;

import com.agilejerry.springmaster.service.EventAssistService;
import com.agilejerry.springmaster.service.EventService;
import com.agilejerry.springmaster.test.BaseTestCase;

public class EventServiceWithoutMockTest extends BaseTestCase {

    @Autowired
    EventService eventService;

    @Autowired
    EventAssistService eventAssistService;

    @Before
    public void setup() {
        Assert.assertNotNull(eventService);
       
    }

    @Test
    public void testReal1(){
        System.out.println("EventServiceWithoutMockTest_testReal1");
        System.out.println(eventAssistService.toString());
        Assert.assertEquals(28, eventService.calc(1));
    }

    @Test
    public void testReal2(){
        Assert.assertEquals(64, eventService.calc(2));
    }

}

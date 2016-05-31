package com.agilejerry.springmaster.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;

import com.agilejerry.springmaster.service.EventService;
import com.agilejerry.springmaster.test.BaseTestCase;

public class EventServiceTest extends BaseTestCase {

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
    }
    @Test
    public void testMock() {
        Assert.assertEquals(11, mockEventService.calc(102));
        Assert.assertEquals(0, mockEventService.calc(100));
    }

}

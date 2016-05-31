package com.agilejerry.springmaster.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.agilejerry.springmaster.service.EventAssistService;
import com.agilejerry.springmaster.service.EventService;
import com.agilejerry.springmaster.test.BaseTestCase;

public class EventServiceWithInjectMocksTest extends BaseTestCase {

    @InjectMocks
    @Autowired
    EventService eventService;

    @Mock 
    EventAssistService eventAssistService;

    @Before
    public void setup() {
        Assert.assertNotNull(eventService);        
        MockitoAnnotations.initMocks(this);
        when(eventAssistService.calcByAssist(2)).thenReturn(2);
    }

    @Test
    public void testWithMockAssist(){
        Assert.assertEquals(2, eventService.calc(1));
        verify(eventAssistService).calcByAssist(2);  
        verify(eventAssistService, times(1)).calcByAssist(2);
    }
 

}

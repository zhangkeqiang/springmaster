package com.agilejerry.springmaster.test;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilejerry.springmaster.service.test.EventServiceTest;
import com.agilejerry.springmaster.service.test.EventServiceWithoutMockTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationTestContext.xml") 
public abstract class BaseTestCase {
    protected final Logger log = LogManager.getLogger(this);

}

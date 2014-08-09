package com.core.base;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;

import com.core.util.SpringContextUtil;

public class BaseTestCase extends TestCase {

    protected static ApplicationContext ac = SpringContextUtil.loadContext();;
    
    public void testDefault(){
    	System.out.println("test is ok.");
    }
}

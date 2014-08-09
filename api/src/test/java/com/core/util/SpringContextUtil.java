package com.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringContextUtil {

    public static ApplicationContext loadContext() {

        return new FileSystemXmlApplicationContext("/src/main/resources/WEB-INF/applicationContext.xml");
    }
}

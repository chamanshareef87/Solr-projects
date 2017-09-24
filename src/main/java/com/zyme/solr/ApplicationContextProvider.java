package com.zyme.solr;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
	}

	public  static ApplicationContext getApplicationContext() {
        return context;
    }
 
}

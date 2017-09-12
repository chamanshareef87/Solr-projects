package com.zyme.solr;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextLoader {

	private static ClassPathXmlApplicationContext context;

	public ApplicationContextLoader() {
		super();
		context = new ClassPathXmlApplicationContext("spring.xml");
	}
	
	public static ClassPathXmlApplicationContext getContext(){
		return context;
	}
	
}

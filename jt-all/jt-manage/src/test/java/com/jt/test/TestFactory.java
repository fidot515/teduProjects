package com.jt.test;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactory {
	@Test
	public void testStaticFactory(){
		//1.实例化spring容器
		ApplicationContext  app = 
				new ClassPathXmlApplicationContext("spring/factory.xml");
		//2.获取对象
		Calendar calendar = (Calendar) app.getBean("calendar1");
		System.out.println("获取当前时间:"+calendar.getTime());
	}
	
	@Test
	public void testInstanceFacotory(){
		//1.实例化spring容器
		ApplicationContext  app = 
				new ClassPathXmlApplicationContext("spring/factory.xml");
		//2.获取对象
		Calendar calendar = (Calendar) app.getBean("calendar2");
		System.out.println("获取当前时间:"+calendar.getTime());
	}
	
	@Test
	public void testSpringFacotory(){
		//1.实例化spring容器
		ApplicationContext  app = 
				new ClassPathXmlApplicationContext("spring/factory.xml");
		//2.获取对象
		Calendar calendar = (Calendar) app.getBean("calendar3");
		System.out.println("获取当前时间:"+calendar.getTime());
	}
}

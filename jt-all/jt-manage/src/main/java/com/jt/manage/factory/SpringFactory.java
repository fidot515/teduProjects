package com.jt.manage.factory;

import java.util.Calendar;

import org.springframework.beans.factory.FactoryBean;

/**
 * 
 * @author LYJ
 *
 */
public class SpringFactory implements FactoryBean<Calendar>{

	@Override
	public Calendar getObject() throws Exception {
		// TODO Auto-generated method stub
		return Calendar.getInstance();
	}

	@Override
	public Class<?> getObjectType() {
		
		return Calendar.class;
	}
	
	//是否单例
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
}

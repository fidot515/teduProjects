package com.jt.manage.factory;

import java.util.Calendar;

public class StaticFactory {
	
	public static Calendar getInstance(){
		
		//JDK原生提供了实例化方法.
		return Calendar.getInstance();
	}
}

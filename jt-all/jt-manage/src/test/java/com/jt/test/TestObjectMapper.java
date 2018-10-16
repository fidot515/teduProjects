package com.jt.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

public class TestObjectMapper {
	
	@Test
	public void javaToJSON() throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		User user = new User();
		user.setId(1);
		user.setName("tomcat");
		user.setAge(10);
		
		String userJSON = 
				objectMapper.writeValueAsString(user);
		System.out.println(userJSON);
		
		//将json串转化为java对象
		User user1 =  objectMapper.readValue(userJSON, User.class);
		System.out.println(user1.toString());
	}
	
	
	@Test
	public void listToJSON() throws IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> strList = new ArrayList<>();
		strList.add("a");
		strList.add("b");
		strList.add("c");
		strList.add("d");
		strList.add("e");
		String listJSON = 
		objectMapper.writeValueAsString(strList);
		System.out.println(listJSON);
		
		//将JSON转化为List集合
		List<String> jsonList = 
		objectMapper.readValue(listJSON, List.class);
		
		System.out.println(jsonList);
	}
	
	
	@Test
	public void ListUserToJSON() throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		User user1 = new User();
		user1.setId(1);
		user1.setName("tomcat");
		user1.setAge(10);
		
		User user2 = new User();
		user2.setId(1);
		user2.setName("tomcat");
		user2.setAge(10);
		
		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		String userListJSON = 
		objectMapper.writeValueAsString(userList);
		System.out.println(userListJSON);
		
		//将userListJSON转化为java对象
		ArrayList<User>  arrayUserList = 
		objectMapper.readValue(userListJSON, ArrayList.class);
		System.out.println(arrayUserList);
		
		User[] users = objectMapper.readValue(userListJSON, User[].class);
		List<User> arraysUser = Arrays.asList(users);
		System.out.println(arraysUser);
	}
	
	
	
	
	
	
	
}

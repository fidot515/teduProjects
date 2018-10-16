package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.sun.tools.corba.se.idl.PragmaEntry;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private HttpClientService httpClient;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
	/**
	 * 问题:
	 * 	因为前台项目只负责数据的展现,不负责数据更新操作
	 * 
	 * 如何将数据传给sso单点登录系统???
	 * HTTP协议 GET/POST
	 * HTTPClient技术:java代码中发起Http请求的
	 * 
	 */
	@Override
	public void saveUser(User user) {
		String url = "http://sso.jt.com/user/register";
		Map<String,String> params = new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		//前台通过httpClient将数据进行远程传输.如果程序在后台执行错误!!
		String result = httpClient.doPost(url, params);
		try {
			//检测返回值结果是否正确
			SysResult sysResult = objectMapper.readValue(result, SysResult.class);
			if(sysResult.getStatus() != 200){
				//表示程序有错
				throw new RuntimeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	
	/**
	 * 1.定义远程url
	 * 2.封装参数
	 * 3.发起httpClient请求 resultJSON   sysResultJSON
	 * 4.判断返回值的状态是否为200,如果不是200  抛出异常
	 * 5.如果状态码为200.获取token数据之后返回.
	 */
	@Override
	public String findUserByUP(User user) {
		String token = null;
		String url = "http://sso.jt.com/user/login";
		Map<String,String> params = new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		String resultJSON = httpClient.doPost(url, params);
		try {	
			SysResult sysResult = 
				objectMapper.readValue(resultJSON, SysResult.class);
			if(sysResult.getStatus() == 200){
				//获取token数据
				token = (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}
	
	
	
	
	
	
	
	

}

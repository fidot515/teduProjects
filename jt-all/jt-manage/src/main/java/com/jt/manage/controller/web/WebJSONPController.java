package com.jt.manage.controller.web;

import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

@Controller
@RequestMapping("/web")
public class WebJSONPController {
	
	//http://manage.jt.com/web/testJSONP?callback=jQuery111102185225838437006_1537844552339&_=1537844552340
	//@RequestMapping(value="/testJSONP",produces="text/html;charset=utf-8")
	//@ResponseBody
	public String jsonp(String callback) throws JsonProcessingException{
		User user = new User();
		user.setId(100);
		user.setName("tomcat猫");
		ObjectMapper objectMapper = new ObjectMapper();
		String userJSON = objectMapper.writeValueAsString(user);
		//因为返回值必须添加回调函数,否则无法解析. hello(data)
		return callback+"(" + userJSON + ")";
	}
	
	//利用Spring中的JSONP返回
	@RequestMapping(value="/testJSONP")
	@ResponseBody
	public MappingJacksonValue jsonpSuper(String callback){
		User user = new User();
		user.setId(100);
		user.setName("tomcat猫");
		MappingJacksonValue value = new MappingJacksonValue(user);
		value.setJsonpFunction(callback);
		return value;
	}
}

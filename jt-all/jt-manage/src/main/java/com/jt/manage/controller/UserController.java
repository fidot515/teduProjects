package com.jt.manage.controller;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	

	@Autowired
	private UserService userService;
	
	//跳转到用户列表页面
	@RequestMapping("/findAll")
	public String findAll(Model model){
		List<User> userList = userService.findAll();
		//将数据传入页面中用于数据回显.request域
		model.addAttribute("userList", userList);
		
		//后期经过视图解析器跳转页面 并且展现数据
		return "userList";
	}	
}

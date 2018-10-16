package com.jt.manage.service;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jt.manage.mapper.UserMapper;
import com.jt.manage.pojo.User;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	//@Resource    //单个项目时用哪个都可以/如果是分布式项目 最好别用
	private UserMapper userMapper;
	
	@Override
	public List<User> findAll() {
		
		return userMapper.findAll();
	}

}

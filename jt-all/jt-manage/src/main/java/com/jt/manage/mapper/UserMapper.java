package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.manage.pojo.User;

public interface UserMapper {
	//查询user表中的数据    * /字段
	//@Select("select * from user")  
	//@Insert("sql")
	//@Delete("sql")
	//@Update("sql")
	List<User> findAll();
}

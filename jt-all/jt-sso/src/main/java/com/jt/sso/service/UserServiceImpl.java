package com.jt.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 思考:
	 * 	admin/1   表示校验用户名为admin的数据
	 * 	sql: select count(*) from tb_user where username = "admin"
	 * 	Type为类型，可选参数1 username、2 phone、3 email
	 */
	@Override
	public boolean findCheckUser(String param, int type) {
		String cloumn = null;
		switch (type) {
			case 1:
				cloumn = "username"; break;
			case 2:
				cloumn = "phone"; break;
			case 3:
				cloumn = "email"; break;
		}
		int count = userMapper.findCheckUser(param,cloumn);
		//如果返回true表示用户已经存在
		return count == 0 ? false : true;
	}
	
	//编辑sso新增业务 1.数据不全
	@Override
	public void saveUser(User user) {
		String md5Pass = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Pass);//将密码进行加密
		user.setEmail(user.getPhone());//暂时代替,否则入库报错
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
	}
	
	/**
	 * 编辑sso 业务实现
	 * 1.根据用户名和密码查询数据 检查数据是否正确
	 * 	 无数据: 用户名和密码不正确  直接返回 null  throw
	 * 	 有数据: 用户名和密码正确
	 * 2.根据加密算法 生成tokenmd5
	 * 	（“JT_TICKET_” + System.currentTime + username）
	 * 3.将用户信息转化为userJSON数据.将token:userJSON保存到redis中
	 * 4.将token数据返回.
	 * 
	 */
	@Override
	public String findUserByUP(User user) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		User userDB = userMapper.findUserByUP(user);
		
		String returnToken = null;
		if(userDB == null){
			throw new RuntimeException();
		}
		
		String token = "JT_TICKET_" + System.currentTimeMillis() + user.getUsername();
		returnToken = DigestUtils.md5Hex(token);
		try {
			String userJSON = objectMapper.writeValueAsString(userDB);
			jedisCluster.setex(returnToken, 3600 * 24 * 7, userJSON);
			System.out.println("用户单点登录成功!!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return returnToken;
	}
	
	
	
	
	
	
	
	
	
}

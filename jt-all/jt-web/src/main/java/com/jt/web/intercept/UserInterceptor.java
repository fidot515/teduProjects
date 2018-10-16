package com.jt.web.intercept;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * 1.在调用controller方法之前拦截
	 * boolean 代表   
	 * 		true代表放行  false表示拦截
	 * 拦截器使用用户登陆校验
	 * 1.获取客户端端的Cookie
	 * 2.判断cookie是否有token数据
	 * 3.判断redis中是否有用户json数据
	 * 如果用户都满足要求则放行.否则跳转登录页面
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取客户端Cookie
		Cookie[] cookies = request.getCookies();
		//2.获取token数据
		String token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token = cookie.getValue();
				break;
			}
		}
		if(!StringUtils.isEmpty(token)){
			//表示用户已经含有token,判断redis中是否有数据
			String userJSON = jedisCluster.get(token);
			
			if(!StringUtils.isEmpty(userJSON)){
				
				User user = objectMapper.readValue(userJSON, User.class);
				//request.getSession().setAttribute("JT_USER", user);
				//将User数据保存到ThreadLocal中
				UserThreadLocal.set(user);
				return true;//表示用户已经登陆 可以放行
			}
		}
		//如果程序执行到这里表示用户登陆有误,重定向到登陆页面
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	//在业务逻辑执行完成后拦截
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	//在业务逻辑执行完之后返回给客户端之前拦截
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		//为了防止内存泄漏关闭ThreadLocal
		UserThreadLocal.remove();
		
	}

}

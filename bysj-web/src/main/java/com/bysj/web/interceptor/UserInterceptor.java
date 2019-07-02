package com.bysj.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bysj.common.po.User;
import com.bysj.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * 判断是否登陆
	 * 1.获取用户的cookie获取token数据
	 * 2.判断token中是否有数据 
	 * 3.没数据就没登陆 转到登陆界面
	 * 4.有数据表示之前登陆过  从redis中根据token获取userJSON：有数据放行？没数据重定向到登陆页面
	 * 3.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		//判断token是否有数据
		if(token != null) {
			//判断redis集群中是否有数据
			String userJSON = jedisCluster.get(token);
			if(userJSON != null) {
				//用户已登录放行
				User user = objectMapper.readValue(userJSON, User.class);
				UserThreadLocal.setUser(user);
				return true;
			}
		}
		//配置重定向
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		UserThreadLocal.remove();
	}

	
}

package com.bysj.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bysj.common.po.User;
import com.bysj.common.vo.SysResult;
import com.bysj.web.service.UserService;
import com.bysj.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;

import redis.clients.jedis.JedisCluster;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster JedisCluster;
	//http://www.bsweb.com/user/login.html  //通用跳转
	@RequestMapping(value="/{login}")
	private String toModule(@PathVariable String login) {
		return login;
	}
	
	@RequestMapping("/doRegister")
	@ResponseBody
	private SysResult saveUser(User user) {
		System.out.println(user.toString());
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "用户新增失败");
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		//根据用户名和密码验证用户
	try {
		String token = userService.findUserByUP(user);
		if(StringUtils.isEmpty(token)) {
			throw new RuntimeException();
		}
		Cookie cookie = new Cookie("JT_TICKET",token);
		cookie.setPath("/");	//表示cookie的权限
		cookie.setMaxAge(3600 * 24 * 7);//保存7天
		response.addCookie(cookie);
		return SysResult.oK();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return SysResult.build(201, "登陆失败");
	}
	
	//用户退出logout.html
	/**
	 * 1.删除redis缓存
	 * 2.删除cookie
	 * 3.重定向到登陆页面
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		Cookie newCookie = null;
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("JT_TICKET")) {
				token = cookie.getValue();
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				newCookie = cookie;
				break;
			}
		}
		JedisCluster.del(token);
		response.addCookie(newCookie);
		return "redirect:/index.html";
	}


	@RequestMapping("/myInformation")
	public String myInformation(Model model) {
		Long userId = UserThreadLocal.getUser().getId();
		Map<String,String> map = userService.findUserById(userId);
		model.addAttribute("map",map);
		return "myInformation";
	}

}

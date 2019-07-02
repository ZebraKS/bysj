package com.bysj.sso.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bysj.common.po.User;
import com.bysj.common.vo.SysResult;
import com.bysj.sso.service.UserSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;

import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserSerive userService;

	@Autowired
	private JedisCluster JedisCluster;
	// http://sso.jt.com/user/findAll
	// 用户信息校验
	/**
	 * 其中chenchen是校验的数据 Type为类型，可选参数1 username、2 phone、3 email
	 * 校验时只查询数据记录总数,总数为0用户可以使用，总数大于0不能用
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public MappingJacksonValue findChechUser(@PathVariable String param, @PathVariable Integer type,String callback) {
		// 判断用户信息是否存在
		boolean flag = userService.findCheckUser(param, type);
		MappingJacksonValue value = new MappingJacksonValue(SysResult.oK(flag));
		value.setJsonpFunction(callback);//添加回调方法
		return value;
	}
	
	//http://sso.jt.com/user/register
	/**
	 * 实现用户新增
	 */
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "用户新增失败");
	}
	
	//实现登陆
	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByUP(User user) {
		try {
			String token = userService.findUserByUP(user);
			if(StringUtils.isEmpty(token)) {
				throw new RuntimeException();
			}
			return SysResult.oK(token);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SysResult.build(201, "登陆失败");
	}
	
	//登陆后账号回显http://sso.jt.com/user/query/
	/**
	 * 根据token查询用户信息,返回user的JSON串
	 */
	@RequestMapping("/query/{token}")
	@ResponseBody
	public MappingJacksonValue findUserByToken(@PathVariable String token,String callback) {
		//获取redis集群中JSON串
		String userJSON = JedisCluster.get(token);
		MappingJacksonValue value = null;
		if(StringUtils.isEmpty(userJSON)) {
			value = new MappingJacksonValue(SysResult.build(201, "用户查询数据失败"));
		}else {
			value = new MappingJacksonValue(SysResult.oK(userJSON));
		}
		value.setJsonpFunction(callback);
		return value;
	}

	/**
	 * 打开我的个人信息，根据id查询User
	 * @param id
	 * @return
	 */
	@RequestMapping("/myInformation")
	@ResponseBody
	public SysResult findUserById(Long id) {
		try {
			User user = userService.findUserById(id);
			return SysResult.oK(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "用户信息查询失败");
	}

	/**
	 * 修改个人信息
	 * @param user
	 * @param callback
	 * @return
	 */
	@RequestMapping("/changeUser")
	@ResponseBody
	public MappingJacksonValue changeUser(User user , String callback){
		try {
			userService.changeUser(user);
			if (user.getPassword()==null){
				MappingJacksonValue value = new MappingJacksonValue(SysResult.oK());
				value.setJsonpFunction(callback);
				return value;
			}else {
				MappingJacksonValue value = new MappingJacksonValue(SysResult.build(200,"logout",null));
				value.setJsonpFunction(callback);
				return value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MappingJacksonValue value = new MappingJacksonValue(SysResult.build(201,"修改失败！"));
		value.setJsonpFunction(callback);
		return value;
	}
}
